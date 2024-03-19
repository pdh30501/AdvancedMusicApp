package com.example.myapplication.ui.adapters.viewholders;

import android.view.View;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.TextViewCompat;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.R;
import com.example.myapplication.glide.audiocover.AudioFileCover;
import com.example.myapplication.ui.adapters.BaseRecyclerViewAdapter;
import com.example.myapplication.ui.adapters.models.BaseRecyclerViewItem;
import com.example.myapplication.ui.adapters.models.SongRecyclerViewItem;

public class SongViewHolder extends BaseViewHolder{

    private SongRecyclerViewItem m_vItem;
    private LinearLayout m_vRootView;

    private ConstraintLayout m_vImageView_Parent;

    private TextView m_vTextView_Title;
    private TextView m_vTextView_Artist;

    private ImageView m_vImageView_Art;

    public SongViewHolder(@NonNull View itemView) {

        super(itemView);
        this.m_vRootView = findViewById(R.id.item_root_view);
        this.m_vImageView_Parent=findViewById(R.id.item_song_art_image_view_parent);
        this.m_vTextView_Title  = findViewById(R.id.item_song_title_text_view);
        this.m_vTextView_Artist  = findViewById(R.id.item_song_artist_text_view);
        this.m_vImageView_Art = findViewById(R.id.item_song_art_image_view);}

    @Override
    public void onInitializeView(BaseRecyclerViewAdapter.ViewType viewType) {
        switch (viewType){
            case GRID:
                break;
            case LIST:
                this.m_vRootView.setOrientation(LinearLayout.HORIZONTAL);
                this.m_vImageView_Parent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, itemView.getResources().getDimensionPixelSize(R.dimen.item_library_song_art_size)));
                break;
        }
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewItem viewItem) {

        SongRecyclerViewItem item = (SongRecyclerViewItem) viewItem;

        this.m_vTextView_Title.setText(viewItem.getTitle());
        Glide.with(itemView.getContext())
                .load(new AudioFileCover(item.getFilePath()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.album_24px)
                .into(this.m_vImageView_Art);
    }
}
