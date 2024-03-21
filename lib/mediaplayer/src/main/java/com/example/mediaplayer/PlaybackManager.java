package com.example.mediaplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.lights.LightsManager;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.session.PlaybackState;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;

import androidx.annotation.IntDef;

import com.example.mediaplayer.interfaces.IPlaybackCallback;
import com.example.mediaplayer.model.Song;
import com.example.mediaplayer.ultis.PlaybackListener;
import com.example.mediaplayer.ultis.PlaybackSubThread;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class PlaybackManager {
    public static final String TAG =PlaybackManager.class.getSimpleName();

    public final int THREAD_UPDATE_INTERVAL=350; //SMOOTH UI UPDATE

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({REPEAT_TYPE_NONE, REPEAT_TYPE_ONE,REPEAT_TYPE_ALL })
    public @interface RepeatType {}

    public static final int REPEAT_TYPE_NONE=0;

    public static final int REPEAT_TYPE_ONE=1;
    public static final int REPEAT_TYPE_ALL=2;
    @RepeatType
    public int m_vRepeatType=REPEAT_TYPE_NONE;
    private int m_vPlaybackState;
    private boolean m_vIsPrepared;
    private boolean m_vPlayOnFocusGain;
    private final IPlaybackCallback m_vCallback;

    private final AudioManager m_vAudioManager;
    private final PlaybackListener m_vListener;

    private final android.os.Handler m_vUIHandler;
    private final Context m_vContext;
    private AudioFocusRequest m_vAudioFocusRequest;
    private MediaPlayer m_vMediaPlayer;
    private  int m_vCurrentQueueIndex;
    private Song m_vCurrentSong;

    private List<Integer> m_vQueue;
    private TreeMap<Integer,Song> m_vSongs;
    private List<PlaybackSubThread> m_vThreads;

    public PlaybackManager(Context context, IPlaybackCallback playbackCallback){
        this.m_vContext = context;
        this.m_vCallback = playbackCallback;

        this.m_vListener = new PlaybackListener(this);
        this.m_vUIHandler = new Handler(); // coi chừng chỗ này có lỗi
        this.m_vAudioManager = (AudioManager) context.getSystemService(AudioManager.class);

        this.m_vQueue = new ArrayList<>();
        this.m_vThreads = new ArrayList<>();

        this.m_vSongs = LibraryManager.getTreemapOfSongs(LibraryManager.getSongs(this.m_vContext));
    }
    private void onAbandonAudioFocus(){
        if(this.m_vAudioFocusRequest != null){
            this.m_vAudioManager.abandonAudioFocusRequest(this.m_vAudioFocusRequest);
        }
    }
    private boolean onGetAudioFocus(){
        if(this.m_vAudioFocusRequest == null){
            AudioFocusRequest.Builder builder = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setFocusGain(AudioManager.AUDIOFOCUS_GAIN)
                    .setAcceptsDelayedFocusGain(true)
                    .setOnAudioFocusChangeListener(this.m_vListener)
                    .setAudioAttributes(new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build());

                this.m_vAudioFocusRequest = builder.build();
        }
        return this.m_vAudioManager.requestAudioFocus(this.m_vAudioFocusRequest) != AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }

    private void onPlay(){
        this.m_vMediaPlayer.start();
        this.m_vPlaybackState = PlaybackState.STATE_PLAYING;
        this.onUpdatePlaybackState();
    }
    private void onRelease(){
        if(this.m_vMediaPlayer != null){
            this.m_vMediaPlayer.reset();
            this.m_vMediaPlayer.release();
            this.m_vMediaPlayer = null;
        }
    }
    private void onStartMediaPlayer(){
        this.m_vIsPrepared = true;
        if(!this.m_vPlayOnFocusGain){
            this.onPlay();
        }
    }
    private void onStoppingThread(){
        if(this.m_vThreads.size()>0){
            for(PlaybackSubThread thread:this.m_vThreads){
                thread.onStop();
                this.m_vThreads.remove(thread);
            }
        }
    }
    private void onUpdatePlaybackState(){
        if (this.m_vCallback == null)
            return;;

            this.onStoppingThread();
            if(this.m_vPlaybackState == PlaybackState.STATE_PLAYING){
                PlaybackSubThread thread = new PlaybackSubThread(THREAD_UPDATE_INTERVAL, this);
                thread.getWorker().setName("Playback Thread");
                this.m_vThreads.add(thread);
                thread.onStart();
            }
            else {
                @SuppressLint("WrongConstant")
                PlaybackState.Builder builder = new PlaybackState.Builder()
                        .setActions(getAvailableActions())
                        .setState(this.getPlaybackState(), this.getPlaybackState(), 1.0F, SystemClock.elapsedRealtime());

                this.m_vCallback.onPlaybackStateChanged(builder.build());
            }
    }
    //Playback Actions (Notification uses

    public boolean canPlayNext(){
        return (this.m_vCurrentQueueIndex + 1) < this.m_vQueue.size();
    }
    public boolean canPlayPrev(){
        return (this.m_vCurrentQueueIndex -1) >= 0;
    }
    public long getAvailableActions(){
        long actions = PlaybackState.ACTION_PLAY;
        if(this.isPlaying()){
            actions |= PlaybackState.ACTION_PAUSE;
        }

        return actions | PlaybackState.ACTION_STOP | PlaybackState.ACTION_SKIP_TO_PREVIOUS | PlaybackState.ACTION_SKIP_TO_NEXT | PlaybackState.ACTION_SEEK_TO;
    }
    public int getCurrentQueueIndex(){
        return m_vCurrentQueueIndex;
    }
    public Song getCurrentSong(){
        return this.m_vCurrentSong;
    }
    public int getPlaybackPosition(){
        if(this.isPlaying()){
            return this.m_vMediaPlayer.getCurrentPosition();
        }
        return 0;
    }
    public int getPlaybackState(){
        return this.m_vPlaybackState;
    }
    public IPlaybackCallback getPlaybackCallback(){
        return this.m_vCallback;
    }

    public boolean isPlaying(){
        return (!this.m_vPlayOnFocusGain && this.m_vMediaPlayer != null && this.m_vPlaybackState == PlaybackState.STATE_PLAYING && this.m_vMediaPlayer.isPlaying());
    }
    public boolean isPlayingOrPaused(){
        return (isPlaying() || (this.m_vMediaPlayer!= null && this.m_vPlaybackState == PlaybackState.STATE_PAUSED));
    }


    public void onAudioCompleted(){
        if(this.isPlaying()){
            this.onStop();
        }
        else {
            switch (this.m_vPlaybackState){
                case REPEAT_TYPE_NONE:
                    break;

                case REPEAT_TYPE_ONE:
                    onPlayIndex(this.m_vCurrentQueueIndex);
                    break;

                case REPEAT_TYPE_ALL:
                    if(canPlayNext()){
                        this.onPlayNext();
                    }
                    else
                        this.onPlayIndex(0);
                    break;
            }

            this.m_vPlaybackState = PlaybackState.STATE_PAUSED;
            this.m_vMediaPlayer.pause();
            this.m_vMediaPlayer.seekTo(0);
            this.onUpdatePlaybackState();
        }
    }
    public void onAudioFocusChanged(int focusChange){
        switch (focusChange){
            case AudioManager.AUDIOFOCUS_GAIN:{
                if (this.m_vPlayOnFocusGain){
                    this.m_vPlayOnFocusGain=false;
                    this.onPlay();
                }
                return;
            }
            //we should also add duck focus but we will add that later
            default:
                if (isPlaying()){
                    onPause();
                    this.m_vPlayOnFocusGain=true;
                }

        }

    }
    public void onPlayIndex(int queueIndex){
        int id =this.m_vQueue.get(queueIndex);
        Song songToPlay = this.m_vSongs.get(id);
        boolean isSameSong=(this.m_vCurrentSong !=null && queueIndex ==this.m_vCurrentQueueIndex && this.m_vCurrentSong.getId()==id);
        if (this.m_vMediaPlayer ==null) {
            this.m_vMediaPlayer = new MediaPlayer();
            this.m_vMediaPlayer.setWakeMode(this.m_vContext, PowerManager.PARTIAL_WAKE_LOCK);
            this.m_vMediaPlayer.setOnCompletionListener(this.m_vListener);
            this.m_vMediaPlayer.setOnPreparedListener(this.m_vListener);
        }
        else if (!isSameSong){
            this.m_vPlaybackState=PlaybackState.STATE_NONE;
            this.m_vMediaPlayer.reset();
            this.m_vIsPrepared= false;
        }
        this.m_vPlayOnFocusGain=this.onGetAudioFocus();
        if (!isSameSong) {
            this.m_vCallback.onUpdateMetadata(songToPlay);
            this.m_vCurrentSong = songToPlay;
            this.m_vCurrentQueueIndex = queueIndex;
            if (songToPlay != null) {//check not really needed here
                try {
                    this.m_vMediaPlayer.setDataSource(songToPlay.getData());
                } catch (Exception ignore) {
                }
                this.m_vMediaPlayer.prepareAsync();
            } else {
                this.onStartMediaPlayer();
            }
        }
        else {
            this.onStartMediaPlayer();
        }
    }

    public void onPause(){
        if (this.m_vMediaPlayer == null)
            return;
        if (this.isPlaying()){
            this.m_vMediaPlayer.pause();
        }
        this.m_vPlaybackState =PlaybackState.STATE_PAUSED;
        this.onUpdatePlaybackState();
    }
    public void onPlayPause(){
        if (this.m_vMediaPlayer == null)
            return;
        if (this.m_vMediaPlayer.isPlaying()){
            this.onPause();
        }
        else{
            this.onPlayIndex(this.m_vCurrentQueueIndex);
        }
    }
    public void onPlayNext(){
        if (this.canPlayNext())
            this.onPlayIndex(this.m_vCurrentQueueIndex++);
    }
    public void onPlayPrevious(){
        if (this.canPlayPrev())
            this.onPlayIndex(this.m_vCurrentQueueIndex--);
    }

    public void onSeekTo(long position){
        if (this.m_vMediaPlayer != null && isPlayingOrPaused()){
            this.m_vMediaPlayer.seekTo((int)position);
        }
    }
    public void onSetQueue(List<Integer> queue){
        this.m_vQueue=queue;
    }
    public void onSetRepeatState(@RepeatType int repeatState){
        this.m_vRepeatType=repeatState;
    }

    public void onStartMediaPlayer(MediaPlayer mediaPlayer){
            if (this.m_vMediaPlayer !=mediaPlayer)
                return;

            this.onStartMediaPlayer();
        }
    public void onStop(){
        if (this.m_vMediaPlayer !=null) {
            this.m_vPlaybackState= PlaybackState.STATE_STOPPED;
            onUpdatePlaybackState();
            onAbandonAudioFocus();
            onRelease();
        }
    }
    public void onUpdateIndex(int index){
        this.m_vCurrentQueueIndex=index;
    }
}
