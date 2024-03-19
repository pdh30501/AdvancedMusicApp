package com.example.myapplication.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.ui.adapters.models.BaseRecyclerViewItem;
import com.example.myapplication.ui.adapters.viewholders.BaseViewHolder;
import com.example.myapplication.ui.adapters.viewholders.SongViewHolder;

import java.util.List;

public class LibraryRecyclerViewAdapter extends  BaseRecyclerViewAdapter{
    public LibraryRecyclerViewAdapter(List<BaseRecyclerViewItem> items) {
        super(items);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BaseRecyclerViewItem.ItemType itemType = BaseRecyclerViewItem.ItemType.values()[viewType];

        switch (itemType)
        {
            case SONG:
                return new SongViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song_layout_sample, parent, false), BaseViewHolder.ViewType.LIST);
            default:
                return null;
        }

    }
}
