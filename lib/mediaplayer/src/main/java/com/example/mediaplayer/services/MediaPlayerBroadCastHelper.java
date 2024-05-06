package com.example.mediaplayer.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.mediaplayer.PlaybackManager;
import com.example.mediaplayer.interfaces.ICustomBroadcastReceiver;
import com.example.mediaplayer.statics.IntentFields;

import java.util.List;

public class MediaPlayerBroadCastHelper {

    private final MediaPlayerService m_vService;

    public MediaPlayerBroadCastHelper(MediaPlayerService service) {
        this.m_vService = service;
    }

    private final BroadcastReceiver onPlay = ((ICustomBroadcastReceiver) (context, intent) -> {
        List<Integer> queue = intent.getIntegerArrayListExtra(IntentFields.EXTRA_TRACKS_LIST);
        int index = intent.getIntExtra(IntentFields.EXTRA_TRACK_INDEX, -1);
        if (queue == null) {
            Log.e("MediaPlayerBroadcastHelper", "onPlay : queue is null");
            return;
        }
        Log.e("MediaPlayerBroadcastHelper", "onPlay : " + queue.size());

        if (index < 0 || queue == null || queue.size() == 0)
            return;

        MediaPlayerBroadCastHelper.this.m_vService.onPlay(queue, index);
    }).build();


    private final BroadcastReceiver onStop = ICustomBroadcastReceiver.onCreate((context, intent) -> {
        MediaPlayerBroadCastHelper.this.m_vService.onStop();
    });

    private final BroadcastReceiver onPlayIndex = ((ICustomBroadcastReceiver) (context, intent) -> {
        int index = intent.getIntExtra(IntentFields.EXTRA_TRACK_INDEX, -1);
        if (index < 0)
            return;

        MediaPlayerBroadCastHelper.this.m_vService.onPlayIndex(index);
    }).build();

    private final BroadcastReceiver onPlayNext = ((ICustomBroadcastReceiver) (context, intent) -> {
        MediaPlayerBroadCastHelper.this.m_vService.onPlayNext();
    }).build();

    private final BroadcastReceiver onPlayPrev = ((ICustomBroadcastReceiver) (context, intent) -> {
        MediaPlayerBroadCastHelper.this.m_vService.onPlayPrev();
    }).build();

    private final BroadcastReceiver onPlayPause = ((ICustomBroadcastReceiver) (context, intent) -> {
        MediaPlayerBroadCastHelper.this.m_vService.onPlayPause();
    }).build();

    private final BroadcastReceiver onUpdateQueue = ((ICustomBroadcastReceiver) (context, intent) -> {
        List<Integer> queue = intent.getIntegerArrayListExtra(IntentFields.EXTRA_TRACKS_LIST);
        int i = intent.getIntExtra(IntentFields.EXTRA_TRACK_INDEX, -1);
        if (i < 0 || queue == null || queue.size() == 0)
            return;

        MediaPlayerBroadCastHelper.this.m_vService.onUpdateQueue(queue, i);
    }).build();

    private final BroadcastReceiver setSeekbarPosition = ((ICustomBroadcastReceiver) (context, intent) -> {
        int position = intent.getIntExtra(IntentFields.EXTRA_SEEK_BAR_POSITION, -1);
        if (position < 0)
            return;

        MediaPlayerBroadCastHelper.this.m_vService.setSeekBarPosition(position);
    }).build();

    private final BroadcastReceiver setRepeatState = ((ICustomBroadcastReceiver) (context, intent) -> {
        //@PlaybackManager.RepeatType
        //int state = intent.getIntExtra(IntentFields.EXTRA_REPEAT_STATE, PlaybackManager.REPEAT_TYPE_ALL);
        int state = intent.getIntExtra(IntentFields.EXTRA_REPEAT_STATE, -1);

        //MediaPlayerBroadCastHelper.this.m_vService.setRepeatState(state);
    }).build();


    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public void registerReceivers() {
        this.m_vService.registerReceiver(this.onPlay, new IntentFilter(IntentFields.INTENT_PLAY));
        this.m_vService.registerReceiver(this.onPlayIndex, new IntentFilter(IntentFields.INTENT_PLAY_INDEX));
        this.m_vService.registerReceiver(this.onPlayNext, new IntentFilter(IntentFields.INTENT_PLAY_NEXT));
        this.m_vService.registerReceiver(this.onPlayPrev, new IntentFilter(IntentFields.INTENT_PLAY_PREV));
        this.m_vService.registerReceiver(this.onPlayPause, new IntentFilter(IntentFields.INTENT_PLAY_PAUSE));
        this.m_vService.registerReceiver(this.onStop, new IntentFilter(IntentFields.INTENT_STOP));
        this.m_vService.registerReceiver(this.onUpdateQueue, new IntentFilter(IntentFields.INTENT_UPDATE_QUEUE));
        this.m_vService.registerReceiver(this.setSeekbarPosition, new IntentFilter(IntentFields.INTENT_SET_SEEKBAR));
        this.m_vService.registerReceiver(this.setRepeatState, new IntentFilter(IntentFields.INTENT_CHANGE_REPEAT));
    }

    public void unregisterReceivers() {
        this.m_vService.unregisterReceiver(this.onPlay);
        this.m_vService.unregisterReceiver(this.onStop);
        this.m_vService.unregisterReceiver(this.onPlayIndex);
        this.m_vService.unregisterReceiver(this.onPlayNext);
        this.m_vService.unregisterReceiver(this.onPlayPrev);
        this.m_vService.unregisterReceiver(this.onPlayPause);
        this.m_vService.unregisterReceiver(this.onUpdateQueue);
        this.m_vService.unregisterReceiver(this.setSeekbarPosition);
        this.m_vService.unregisterReceiver(this.setRepeatState);
    }
}

