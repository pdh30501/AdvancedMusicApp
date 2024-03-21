package com.example.mediaplayer;

import android.content.Context;
import android.hardware.lights.LightsManager;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.session.PlaybackState;
import android.os.PowerManager;

import androidx.annotation.IntDef;

import com.example.mediaplayer.interfaces.IPlaybackCallback;
import com.example.mediaplayer.model.Song;
import com.example.mediaplayer.ultis.PlaybackListener;
import com.example.mediaplayer.ultis.PlaybackSubThread;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.PublicKey;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Handler;

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

    private final Handler m_vUIHandler;
    private final Context m_vContext;
    private AudioFocusRequest m_vAudioFocusRequest;
    private MediaPlayer m_vMediaPlayer;
    private  int m_vCurrentQueueIndex;
    private Song m_vCurrentSong;

    private List<Integer> m_vQueue;
    private TreeMap<Integer,Song> m_vSongs;
    private List<PlaybackSubThread> m_vThreads;

    public PlaybackManager(Context context, IPlaybackCallback playbackCallback){ }
    private void onAbandonAudioFocus(){}
    private boolean onGetAudioFocus(){}

    private void onPlay(){}
    private void onRelease(){}
    private void onStartMediaPlayer(){}
    private void onStoppingThread(){}
    private void onUpdatePlaybackState(){}
    //Playback Actions (Notification uses

    public boolean canPlayNext(){}
    public boolean canPlayPrev(){}
    public long getAvailableActions(){}
    public int getCurrentQueueIndex(){}
    public Song getCurrentSong(){}
    public int getPlaybackPosition(){}
    public int getPlaybackState(){}
    public IPlaybackCallback getPlaybackCallback(){}

    public boolean isPlaying(){}
    public boolean isPlayingOrPaused(){}


    public void onAudioCompleted(){}
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
