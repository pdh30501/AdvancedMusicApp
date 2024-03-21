package com.example.myapplication.ui.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ui.adapters.models.BaseRecyclerViewItem;
import com.example.myapplication.ui.adapters.viewholders.BaseViewHolder;

import java.util.List;

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    ViewType m_vLayoutViewType;


    public enum ViewType {
        LIST, GRID
    }
    public void setAdapterViewType(ViewType viewType) {
        this.m_vLayoutViewType = viewType;
        // Gọi notifyDataSetChanged() ở đây để thông báo cho RecyclerView rằng dữ liệu đã thay đổi
        notifyDataSetChanged();
    }

    final List<BaseRecyclerViewItem> m_vItems;

    public BaseRecyclerViewAdapter(List<BaseRecyclerViewItem> items)
    {
        this.m_vItems = items;
    }

    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position)
    {
        holder.onBindViewHolder(this.m_vItems.get(position));
    }

    @Override
    public int getItemCount() {
        return this.m_vItems.size();
    }

    @Override
    public long getItemId(int position) {
        return this.m_vItems.get(position).getHashCode();
    }

    @Override
    public int getItemViewType(int position) {
        return this.m_vItems.get(position).getItemType().ordinal();
    }
    public ViewType getViewType() {
        return this.m_vLayoutViewType;

    }
}

