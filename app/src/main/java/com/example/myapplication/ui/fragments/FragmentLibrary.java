package com.example.myapplication.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.extensions.gridlayout.GridLayoutManagerExtended;
import com.example.mediaplayer.LibraryManager;
import com.example.mediaplayer.model.Song;
import com.example.myapplication.R;
import com.example.myapplication.ui.adapters.BaseRecyclerViewAdapter;
import com.example.myapplication.ui.adapters.LibraryRecyclerViewAdapter;
import com.example.myapplication.ui.adapters.models.BaseRecyclerViewItem;
import com.example.myapplication.ui.adapters.models.SongRecyclerViewItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FragmentLibrary extends Fragment {
    private View m_vRootView;

    private RecyclerView m_vLibraryRecyclerView;
    private BaseRecyclerViewAdapter m_vLibraryAdapter;
    private GridLayoutManagerExtended m_vGridLayout;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout cho FragmentLibrary từ file layout fragment_library.xml
        m_vRootView = inflater.inflate(R.layout.fragment_library, container, false);
        m_vLibraryRecyclerView = m_vRootView.findViewById(R.id.library_recyclerView);
        return m_vRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Lấy danh sách các bài hát từ MediaManager
        List<Song> songs = LibraryManager.getSongs(getContext());
        List<BaseRecyclerViewItem> items = new ArrayList<>();

        // Tạo danh sách các mục RecyclerView từ danh sách bài hát
        for (Song song : songs) {
            items.add(new SongRecyclerViewItem(song));
        }

        // Khởi tạo Adapter và đặt loại hiển thị ban đầu là LIST
        m_vLibraryAdapter = new LibraryRecyclerViewAdapter(items);
        setAdapterViewType(BaseRecyclerViewAdapter.ViewType.LIST);

        // Đặt Adapter cho RecyclerView
        m_vLibraryRecyclerView.setAdapter(m_vLibraryAdapter);

        // Thêm padding cho RecyclerView để tránh che phủ bởi thanh điều hướng và thanh điều khiển nhạc
        int panelHeights = getResources().getDimensionPixelSize(R.dimen.navigation_bar_height) + getResources().getDimensionPixelSize(R.dimen.mediaplayerbar_height);
        m_vLibraryRecyclerView.setPadding(0, 0, 0, panelHeights);

        // Lắng nghe sự kiện click của nút Floating Action Button để thay đổi loại hiển thị
        FloatingActionButton btn = view.findViewById(R.id.btn_test_layout);
        btn.setOnClickListener(v -> {
            setAdapterViewType((m_vLibraryAdapter.getViewType() == BaseRecyclerViewAdapter.ViewType.LIST) ?
                    BaseRecyclerViewAdapter.ViewType.GRID : BaseRecyclerViewAdapter.ViewType.LIST);
        });
    }


    private void setAdapterViewType(BaseRecyclerViewAdapter.ViewType viewType) {
        this.m_vLibraryAdapter.setAdapterViewType(viewType);
        int rowCount = (viewType == BaseRecyclerViewAdapter.ViewType.LIST) ? 1 : 3;

        // Kiểm tra xem m_vGridLayout đã được khởi tạo chưa
        if (m_vGridLayout == null) {
            // Nếu không, tạo một GridLayoutManager mới và đặt nó cho RecyclerView
            this.m_vGridLayout = new GridLayoutManagerExtended(getContext(), rowCount);
            this.m_vLibraryRecyclerView.setLayoutManager(this.m_vGridLayout);
        } else {
            // Nếu đã có, chỉ cần cập nhật số cột của GridLayoutManager hiện tại
            this.m_vGridLayout.setSpanCount(rowCount);
        }

        // Thông báo cho RecyclerView rằng dữ liệu đã thay đổi và cần cập nhật giao diện
        this.m_vLibraryAdapter.notifyDataSetChanged();
    }


    public <T extends View> T findViewById(@IdRes int id){
        return this.m_vRootView.findViewById(id);
    }
}
