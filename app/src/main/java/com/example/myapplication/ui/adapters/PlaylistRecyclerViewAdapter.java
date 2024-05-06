package com.example.myapplication.ui.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.myapplication.ui.UIThread;
import com.example.myapplication.ui.adapters.helpers.BaseViewHelper;
import com.example.myapplication.ui.adapters.models.BaseRecyclerViewItem;
import com.example.myapplication.ui.adapters.models.PlaylistRecyclerViewItem;
import com.example.myapplication.ui.adapters.models.SongRecyclerViewItem;
import com.example.myapplication.ui.adapters.viewholders.BaseViewHolder;
import com.example.myapplication.ui.adapters.viewholders.PlaylistViewHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PlaylistRecyclerViewAdapter extends BaseRecyclerViewAdapter {
    // Tạo một HashSet để lưu trữ các ViewHolder
    private HashSet<BaseViewHolder> m_vBaseViewHolders;

    public PlaylistRecyclerViewAdapter(List<BaseRecyclerViewItem> items) {
        super(items);
        m_vBaseViewHolders = new HashSet<>();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tạo ViewHolder dựa vào viewType
        BaseRecyclerViewItem.ItemType itemType = BaseRecyclerViewItem.ItemType.values()[viewType];
        switch (itemType) {
            case PLAYLIST:
                BaseViewHolder viewHolder = BaseViewHelper.onCreateViewHolder(PlaylistViewHolder.class, parent);
                viewHolder.onInitializeView(this.m_vLayoutViewType);
                this.m_vBaseViewHolders.add(viewHolder);
                return viewHolder;
            default:
                return null;
        }
    }

    @Override
    //  Ghi đè phương thức onBindViewHolder để gán sự kiện click cho mỗi mục RecyclerView
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        holder.itemView.setOnClickListener((v) -> {
            int i = position;
            // Gọi phương thức onClickPlay của MediaPlayerThread thông qua UIThread
            UIThread.getInstance().getMediaPlayerThread().getCallback().onClickPlay(i, getQueue());
        });
    }

    private List<Integer> getQueue() {
        // Lấy danh sách các ID bài hát từ danh sách mục RecyclerView
        List<Integer> results = new ArrayList<>();
        if (this.m_vItems != null) {
            for (int i = 0; i < this.m_vItems.size(); i++) {
                // Ép kiểu mục RecyclerView thành PlaylistRecyclerViewItem để lấy ID bài hát
                results.add((int)((PlaylistRecyclerViewItem)this.m_vItems.get(i)).getPlaylistId());
            }
        }
        return results;
    }

    @Override
    public int getItemCount() {
        return this.m_vItems.size();
    }
}
