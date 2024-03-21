package com.example.myapplication.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.ui.adapters.helpers.BaseViewHelper;
import com.example.myapplication.ui.adapters.models.BaseRecyclerViewItem;
import com.example.myapplication.ui.adapters.viewholders.BaseViewHolder;
import com.example.myapplication.ui.adapters.viewholders.SongViewHolder;

import java.util.HashSet;
import java.util.List;

public class LibraryRecyclerViewAdapter extends BaseRecyclerViewAdapter {
    private HashSet<BaseViewHolder> m_vBaseViewHolders;

    public LibraryRecyclerViewAdapter(List<BaseRecyclerViewItem> items) {
        super(items);
        m_vBaseViewHolders = new HashSet<>();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseRecyclerViewItem.ItemType itemType = BaseRecyclerViewItem.ItemType.values()[viewType];
        switch (itemType) {
            case SONG:
                BaseViewHolder viewHolder = BaseViewHelper.onCreateViewHolder(SongViewHolder.class, parent);
                viewHolder.onInitializeView(this.m_vLayoutViewType);
                this.m_vBaseViewHolders.add(viewHolder);
                return viewHolder;
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onInitializeView(this.m_vLayoutViewType);
        super.onBindViewHolder(holder, position);
    }

}
