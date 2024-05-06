package com.example.myapplication.ui;

import static android.content.ContentValues.TAG;

import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.util.Log;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.views.panels.RootMediaPlayerPanel;
import com.example.myapplication.views.panels.RootNavigationBarPanel;
import com.realgear.multislidinguppanel.Adapter;
import com.realgear.multislidinguppanel.BasePanelView;
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


        this.m_vMediaPlayerThread = new MediaPlayerThread(activity,getCallback());
        this.m_vMediaPlayerThread.onStart();
    }

    public MediaController.Callback getCallback() {
        return new MediaController.Callback() {
            @Override
            public void onPlaybackStateChanged(@Nullable PlaybackState state) {
                Adapter adapter = UIThread.this.m_vMultiSlidingPanel.getAdapter();
                if (adapter != null) {
                    BasePanelView item = adapter.getItem(RootMediaPlayerPanel.class);
                    if (item != null) {
                        ((RootMediaPlayerPanel) item).onPlaybackStateChanged(state);
                    } else {
                        Log.e("UIThread", "Item is null");
                    }
                } else {
                    Log.e("UIThread", "Adapter is null");
                }                //super.onPlaybackStateChanged(state);

                //if (m_vMultiSlidingPanel != null && m_vMultiSlidingPanel.getAdapter() != null) {
                    // Gọi phương thức getAdapter() và thực hiện các thao tác tiếp theo
                 //   m_vMultiSlidingPanel.getAdapter().getItem(RootMediaPlayerPanel.class).onPlaybackStateChanged(state);
                //} else {
                    // Xử lý trường hợp m_vMultiSlidingPanel null ở đây
                //    Log.e(TAG, "m_vMultiSlidingPanel is null or its adapter is null");
                //}
            }

            @Override
            public void onMetadataChanged(@Nullable MediaMetadata metadata) {
                UIThread.this.m_vMultiSlidingPanel.getAdapter().getItem(RootMediaPlayerPanel.class).onMetadataChanged(metadata);

                //super.onMetadataChanged(metadata);

                //if (metadata != null && m_vMultiSlidingPanel != null && m_vMultiSlidingPanel.getAdapter() != null) {
                   // Adapter adapter = m_vMultiSlidingPanel.getAdapter();
                  //  RootMediaPlayerPanel rootMediaPlayerPanel = adapter.getItem(RootMediaPlayerPanel.class);
                  //  if (rootMediaPlayerPanel != null) {
                  //      rootMediaPlayerPanel.onMetadataChanged(metadata);
                   // } else {
                        // Xử lý trường hợp adapter không chứa RootMediaPlayerPanel
                   //     Log.e(TAG, "m_vMultiSlidingPanel is null or its adapter is null");

                  //  }
             //   } else {
                    // Xử lý trường hợp metadata, m_vMultiSlidingPanel hoặc adapter là null
                //    Log.e(TAG, "m_vMultiSlidingPanel is null or its adapter is null");

               // }
            }


        };
    }

    public static UIThread getInstance() { return instance; }

    public MediaPlayerThread getMediaPlayerThread() {
        return this.m_vMediaPlayerThread;
    }

    public void onCreate() {
        m_vMultiSlidingPanel = findViewById(R.id.root_sliding_up_panel);

        List<Class<?>> items = new ArrayList<>();

        items.add(RootMediaPlayerPanel.class);
        items.add(RootNavigationBarPanel.class);

        m_vMultiSlidingPanel.setPanelStateListener(new PanelStateListener(m_vMultiSlidingPanel));

        m_vMultiSlidingPanel.setAdapter(new Adapter(this.m_vMainActivity,items));
    }



    public <T extends android.view.View> T findViewById(@IdRes int id) {
        return this.m_vMainActivity.findViewById(id);
    }
}