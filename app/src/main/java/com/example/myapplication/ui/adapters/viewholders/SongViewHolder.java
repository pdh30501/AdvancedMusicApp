package com.example.myapplication.ui.adapters.viewholders;

import android.view.View;

import androidx.core.widget.TextViewCompat;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.R;
import com.example.myapplication.glide.audiocover.AudioFileCover;
import com.example.myapplication.ui.adapters.models.BaseRecyclerViewItem;
import com.example.myapplication.ui.adapters.models.SongRecyclerViewItem;

public class SongViewHolder extends BaseViewHolder{


    public SongViewHolder(@NonNull View itemView, ViewType viewTye) {
        super(itemView, viewTye);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewItem viewItem) {

        SongRecyclerViewItem item = (SongRecyclerViewItem) viewItem;

        TextView title = findViewById(R.id.item_song_title);
        ImageView imageView = findViewById(R.id.item_song_art);

        title.setText(viewItem.getTitle());
        Glide.with(itemView.getContext())
                .load(new AudioFileCover(item.getFilePath()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }
}
