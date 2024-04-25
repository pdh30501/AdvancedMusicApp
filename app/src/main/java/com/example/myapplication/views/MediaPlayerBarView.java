package com.example.myapplication.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.media.MediaMetadata;
import android.media.session.PlaybackState;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.example.myapplication.R;
import com.example.myapplication.ui.MediaPlayerThread;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.realgear.multislidinguppanel.MultiSlidingUpPanelLayout;

public class MediaPlayerBarView {
    public static final int STATE_NORMAL = 0;
    public static final int STATE_PARTIAL = 1;

    private final View mRootView;

    private int mState;

    private FrameLayout mBackgroundView;
    private LinearProgressIndicator mProgressIndicator;
    private ConstraintLayout mControlsContainer;
    private ImageView mImageView_Art;
    private TextView mTextView_SongTitle;
    private TextView mTextView_SongArtist;
    private ImageButton mImageBtn_Favorite;
    private ImageButton mImageBtn_PlayPause;
    private PlaybackState prevState = null;

    public MediaPlayerBarView(View rootView) {
        this.mRootView = rootView;
        this.mBackgroundView = findViewById(R.id.media_player_bar_bg);
        this.mControlsContainer = findViewById(R.id.media_player_bar_controls_container);
        this.mProgressIndicator= findViewById(R.id.media_player_bar_progress_indicator);
        this.mImageView_Art = findViewById(R.id.image_view_album_art);
        this.mImageBtn_Favorite = findViewById(R.id.btn_favorite);
        this.mImageBtn_PlayPause = findViewById(R.id.btn_play_pause);
        this.mTextView_SongTitle = findViewById(R.id.text_view_song_title);

        this.mTextView_SongArtist = findViewById(R.id.text_view_song_artist);
        this.mRootView.setAlpha(1.0F);
        this.onInit();
    }

    public View getRootView() {
        return this.mRootView;
    }

    public void onInit() {
        this.mImageBtn_PlayPause.setOnClickListener((v) -> {
            // Khi người dùng nhấp vào nút phát / dừng
            // Gọi đến phương thức onClickPlayPause() trong MediaPlayerThread để xử lý sự kiện
            MediaPlayerThread.getInstance().getCallback().onClickPlayPause();
            // Ghi lại sự kiện đã xảy ra
            Log.i("MediaPlayerBar", "Play Pause Btn Clicked");
        });
    }




    public void onPlaybackStateChanged(PlaybackState state) {
        this.mProgressIndicator.setProgress((int)state.getPosition());

        if (prevState == null || prevState.getState() != state.getState()) {
            this.mImageBtn_PlayPause.setImageIcon(Icon.createWithResource(this.getContext(), (state.getState() == PlaybackState.STATE_PLAYING) ? R.drawable.icon_pause_48px : R.drawable.icon_play_arrow_48px));
        }
    }

    public void onSliding(float slideOffset, int state) {
        float fadeStart = 0.25F;
        float alpha = (slideOffset / fadeStart);

        if (state == STATE_NORMAL) {
            this.mRootView.setAlpha(1F - alpha);
            this.mBackgroundView.setAlpha(1F);
            this.mProgressIndicator.setAlpha(1F);
            this.mControlsContainer.setAlpha(1F);
        }
        else {
            this.mRootView.setAlpha(alpha);
            this.mBackgroundView.setAlpha(0F);
            this.mProgressIndicator.setAlpha(0F);
            this.mControlsContainer.setAlpha(1F);
        }

        this.mState = state;
    }

    public Context getContext() {
        return this.mRootView.getContext();
    }

    public <T extends View> T findViewById(@IdRes int id) {
        return this.mRootView.findViewById(id);
    }

    public void onPanelStateChanged(int panelSate) {
        if (panelSate == MultiSlidingUpPanelLayout.COLLAPSED) {
            this.mRootView.setVisibility(View.VISIBLE);
        }
    }

    public void onUpdateVibrantColor(int vibrantColor) {

        //this.mImageBtn_PlayPause.setBackgroundColor(vibrantColor);
    }

    public void onUpdateVibrantDarkColor(int vibrantDarkColor) {
        this.mBackgroundView.setBackgroundColor(vibrantDarkColor);
    }

    public void onUpdateMutedColor(int mutedColor) {

    }

    public void onUpdateMutedDarkColor(int mutedDarkColor) {
        this.mProgressIndicator.setTrackColor(mutedDarkColor);
    }


    public void onMetadataChanged(MediaMetadata metadata) {


        this.mTextView_SongTitle.setText(metadata.getText(MediaMetadata.METADATA_KEY_TITLE));
        this.mTextView_SongArtist.setText(metadata.getText(MediaMetadata.METADATA_KEY_ARTIST));
        this.mProgressIndicator.setMax((int)metadata.getLong(MediaMetadata.METADATA_KEY_DURATION));

        Bitmap album_art = metadata.getBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART);

        Log.i("Media Player Bar View", "Is bitmap null ? " + (album_art == null));

        if (album_art != null) {
            this.mImageView_Art.setImageBitmap(album_art);
        }
        else
            this.mImageView_Art.setImageDrawable(ResourcesCompat.getDrawable(this.mRootView.getResources(), R.drawable.album_24px, this.mRootView.getContext().getTheme()));

    }
}