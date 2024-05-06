package com.example.myapplication.ui;

import android.media.session.MediaController;

import com.example.mediaplayer.CorePlayer;
import com.example.mediaplayer.interfaces.IPlayerCallback;
import com.example.mediaplayer.statics.ClassManager;
import com.example.myapplication.MainActivity;

public class MediaPlayerThread {
    private final String TAG = getClass().getSimpleName();

    private static MediaPlayerThread instance;

    private CorePlayer m_vCorePlayer;
    private IPlayerCallback m_vCallback;

    public MediaPlayerThread(MainActivity activity, MediaController.Callback callback) {
        ClassManager.init(MainActivity.class);
        this.m_vCorePlayer = new CorePlayer(activity, callback);
        this.m_vCallback = this.m_vCorePlayer.getCallback();

        setInstance(this);
    }

    private void setInstance(MediaPlayerThread instance) {
        if (MediaPlayerThread.instance == null) {
            MediaPlayerThread.instance = instance;
        }
    }

    public static MediaPlayerThread getInstance() {
        return instance;
    }

    public IPlayerCallback getCallback() { return this.m_vCallback; }

    public void onStart() { this.m_vCorePlayer.onStart(); }

    public void onDestroy() { this.m_vCorePlayer.onDestroy(); }
}
