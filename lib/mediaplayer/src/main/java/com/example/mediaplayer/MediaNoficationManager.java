package com.example.mediaplayer;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadata;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;

import com.example.mediaplayer.services.MediaPlayerService;

public class MediaNoficationManager extends BroadcastReceiver {
    public static  final String TAG = MediaNoficationManager.class.getSimpleName();
    public static final String NOTIFICATION_NAME = MediaPlayerService.class.getSimpleName();
    public static final  int NOTIFICATION_ID = 101;

//    private final NotificationManager m_vNotificationManager;
//    private final Notification.Action m_vPlayAction;
//    private final Notification.Action m_vPauseAction;
//    private final Notification.Action m_vPlayNextAction;
//    private final Notification.Action m_vPlayPrevAction;


    private final MediaPlayerService m_vservice;

    private boolean m_vStarted;
    private  boolean isRegistered;

    public MediaNoficationManager(MediaPlayerService service) {
        this.m_vservice = service;

        this.m_vStarted = false;
        this.isRegistered = false;

        String pkgName = this.m_vservice.getPackageName();

        // Todo
    }

    public void onStop() {
        // Todo
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Todo
    }

    public void onUpdateNotification(MediaMetadata mediaMetadata, PlaybackState playbackState, MediaSession.Token sessionToken) {
        // Todo
    }
}
