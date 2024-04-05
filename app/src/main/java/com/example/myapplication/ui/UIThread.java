package com.example.myapplication.ui;

import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.PlaybackState;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.views.panels.RootMediaPlayerPanel;
import com.example.myapplication.views.panels.RootNavigationBarPanel;
import com.realgear.multislidinguppanel.Adapter;
import com.realgear.multislidinguppanel.MultiSlidingUpPanelLayout;
import com.realgear.multislidinguppanel.PanelStateListener;

import java.util.ArrayList;
import java.util.List;

public class UIThread {
    private static UIThread instance;

    private final MainActivity m_vMainActivity;

    private MultiSlidingUpPanelLayout m_vMultiSlidingPanel;
    private RootMediaPlayerPanel mRootMediaPlayerPanel;

    private MediaPlayerThread m_vMediaPlayerThread;

    private boolean m_vCanUpdatePanelsUI;


    public UIThread(MainActivity activity) {
        instance = this;

        this.m_vMainActivity = activity;
        onCreate();


        this.m_vMediaPlayerThread = new MediaPlayerThread(this.m_vMainActivity,getCallback());
        this.m_vMediaPlayerThread.onStart();
    }

    public MediaController.Callback getCallback() {
        return new MediaController.Callback() {
            @Override
            public void onPlaybackStateChanged(@Nullable PlaybackState state) {
                UIThread.this.m_vMultiSlidingPanel.getAdapter().getItem(RootMediaPlayerPanel.class).onPlaybackStateChanged(state);
            }

            @Override
            public void onMetadataChanged(@Nullable MediaMetadata metadata) {
                UIThread.this.m_vMultiSlidingPanel.getAdapter().getItem(RootMediaPlayerPanel.class).onMetadataChanged(metadata);
            }
        };
    }

    public static UIThread getInstance() { return instance; }

    public MediaPlayerThread getMediaPlayerThread() {
        return this.m_vMediaPlayerThread;
    }

    public void onCreate() {
        MultiSlidingUpPanelLayout panelLayout = findViewById(R.id.root_sliding_up_panel);

        List<Class<?>> items = new ArrayList<>();

        items.add(RootMediaPlayerPanel.class);
        items.add(RootNavigationBarPanel.class);

        panelLayout.setPanelStateListener(new PanelStateListener(panelLayout));

        panelLayout.setAdapter(new Adapter(this.m_vMainActivity,items));
    }



    public <T extends android.view.View> T findViewById(@IdRes int id) {
        return this.m_vMainActivity.findViewById(id);
    }
}