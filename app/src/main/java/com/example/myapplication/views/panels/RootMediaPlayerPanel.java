package com.example.myapplication.views.panels;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.session.PlaybackState;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.example.extensions.bottomsheet.CustomBottomSheetBehavior;
import com.example.myapplication.R;
import com.example.myapplication.views.MediaPlayerBarView;
import com.example.myapplication.views.MediaPlayerView;
import com.realgear.multislidinguppanel.BasePanelView;
import com.realgear.multislidinguppanel.IPanel;
import com.realgear.multislidinguppanel.MultiSlidingUpPanelLayout;

public class RootMediaPlayerPanel extends BasePanelView {

    private MediaPlayerView mMediaPlayerView;
    private MediaPlayerBarView mMediaPlayerBarView;



    public RootMediaPlayerPanel(@NonNull Context context, MultiSlidingUpPanelLayout panelLayout) {
        super(context, panelLayout);

        getContext().setTheme(R.style.Theme_MyApplication);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_root_mediaplayer,this,true);


    }

    @Override
    public void onCreateView() {
        // The panel will be collapsed on start of application
        this.setPanelState(MultiSlidingUpPanelLayout.COLLAPSED);
        // The panel will slide up and down
        this.setSlideDirection(MultiSlidingUpPanelLayout.SLIDE_VERTICAL);
        // Sets the panels peak height
        this.setPeakHeight(getResources().getDimensionPixelSize(R.dimen.mediaplayerbar_height));
    }

    @Override
    public void onBindView() {
        mMediaPlayerView=new MediaPlayerView(findViewById(R.id.media_player_view));
        mMediaPlayerBarView=new MediaPlayerBarView(findViewById(R.id.media_player_bar_view));

        DisplayMetrics dm= getResources().getDisplayMetrics();
        FrameLayout layout=findViewById(R.id.media_player_bottom_sheet_behavior);





        CustomBottomSheetBehavior<FrameLayout> bottomSheetBehavior=CustomBottomSheetBehavior.from(layout);
        bottomSheetBehavior.setSkipAnchored(false);
        bottomSheetBehavior.setAllowUserDragging(true);

        bottomSheetBehavior.setAnchorOffset((int)(dm.heightPixels*0.75F));
        bottomSheetBehavior.setMediaPlayerBarHeight(getPeakHeight());
        bottomSheetBehavior.setState(CustomBottomSheetBehavior.STATE_COLLAPSED);

        bottomSheetBehavior.addBottomSheetCallback(new CustomBottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int oldState, int newState) {
                switch (newState){
                    case CustomBottomSheetBehavior.STATE_ANCHORED:
                    case CustomBottomSheetBehavior.STATE_EXPANDED:
                    case CustomBottomSheetBehavior.STATE_DRAGGING:
                        getMultiSlidingUpPanel().setSlidingEnabled(false);
                        break;

                    default:
                        getMultiSlidingUpPanel().setSlidingEnabled(true);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                mMediaPlayerView.onSliding(slideOffset,MediaPlayerView.STATE_PARTIAL);
                mMediaPlayerBarView.onSliding(slideOffset,MediaPlayerBarView.STATE_PARTIAL);

            }
        });

    }
    @Override
    public void onPanelStateChanged(int panelSate) {

    }



    public void onSliding(IPanel<View> panel, int top,int dy, float slidingOffset){
        super.onSliding(panel,top,dy,slidingOffset);

        mMediaPlayerView.onSliding(slidingOffset,MediaPlayerView.STATE_NORMAL);
        mMediaPlayerBarView.onSliding(slidingOffset,MediaPlayerBarView.STATE_NORMAL);

    }

    public void onPlaybackStateChanged(PlaybackState state) {
    }

    public void onMetadataChanged(MediaMetadata metadata) {
    }
}
