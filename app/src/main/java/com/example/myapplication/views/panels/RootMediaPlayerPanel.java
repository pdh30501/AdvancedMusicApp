package com.example.myapplication.views.panels;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.session.PlaybackState;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.example.extensions.bottomsheet.CustomBottomSheetBehavior;
import com.example.myapplication.R;
import com.example.myapplication.theme.AsyncPaletteBuilder;
import com.example.myapplication.theme.interfaces.PaletteStateListener;
import com.example.myapplication.views.MediaPlayerBarView;
import com.example.myapplication.views.MediaPlayerView;
import com.realgear.multislidinguppanel.BasePanelView;
import com.realgear.multislidinguppanel.IPanel;
import com.realgear.multislidinguppanel.MultiSlidingUpPanelLayout;

public class RootMediaPlayerPanel extends BasePanelView implements PaletteStateListener {

    private MediaPlayerView mMediaPlayerView;
    private MediaPlayerBarView mMediaPlayerBarView;

    private AsyncPaletteBuilder mAsyncPaletteBuilder;


    public RootMediaPlayerPanel(@NonNull Context context, MultiSlidingUpPanelLayout panelLayout) {
        super(context, panelLayout);

        getContext().setTheme(R.style.Theme_MyApplication);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_root_mediaplayer,this,true);

        this.mAsyncPaletteBuilder = new AsyncPaletteBuilder(this);

    }

    @Override
    public void onCreateView() {
        // The panel will be collapsed on start of application
        this.setPanelState(MultiSlidingUpPanelLayout.HIDDEN);
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

        ViewGroup.LayoutParams params = layout.getLayoutParams();
        params.height = dm.heightPixels - (mPeakHeight);
        layout.setLayoutParams(params);

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
        if (this.mMediaPlayerBarView != null){
            this.mMediaPlayerBarView.onPanelStateChanged(panelSate);}
        if (this.mMediaPlayerView != null){
            this.mMediaPlayerView.onPanelStateChanged(panelSate);}

    }
 

    public void onPlaybackStateChanged(PlaybackState state) {
        if (state.getState() == PlaybackState.STATE_PLAYING || state.getState() == PlaybackState.STATE_PAUSED ){
            if (this.getPanelState() == MultiSlidingUpPanelLayout.HIDDEN) {

                this.collapsePanel();
            }
        }
        this.mMediaPlayerBarView.onPlaybackStateChanged(state);

    }


    public void onSliding(IPanel<View> panel, int top,int dy, float slidingOffset){
        super.onSliding(panel,top,dy,slidingOffset);

        mMediaPlayerView.onSliding(slidingOffset,MediaPlayerView.STATE_NORMAL);
        mMediaPlayerBarView.onSliding(slidingOffset,MediaPlayerBarView.STATE_NORMAL);

    }

    @Override
    public void onUpdateVibrantColor(int vibrantColor) {
        this.mMediaPlayerBarView.onUpdateVibrantColor(vibrantColor);
    }

    @Override
    public void onUpdateVibrantDarkColor(int vibrantDarkColor) {
        this.mMediaPlayerBarView.onUpdateVibrantDarkColor(vibrantDarkColor);
    }

    @Override
    public void onUpdateVibrantLightColor(int vibrantLightColor) {

    }

    @Override
    public void onUpdateMutedColor(int mutedColor) {
        this.mMediaPlayerBarView.onUpdateMutedColor(mutedColor);
    }

    @Override
    public void onUpdateMutedDarkColor(int mutedDarkColor) {
        this.mMediaPlayerBarView.onUpdateMutedDarkColor(mutedDarkColor);
    }

    public void onMetadataChanged(MediaMetadata metadata) {
        this.mMediaPlayerBarView.onMetadataChanged(metadata);
        //this.mMediaPlayerView.onMetadataChanged(metadata);

        Bitmap bitmap = metadata.getBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART);
        this.mAsyncPaletteBuilder.onStartAnimation(bitmap);
    }
}
