package com.example.myapplication.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.extensions.gridlayout.GridLayoutManagerExtended;
import com.example.mediaplayer.interfaces.ApiService;
import com.example.mediaplayer.model.Playlist;
import com.example.mediaplayer.model.Song;
import com.example.myapplication.R;
import com.example.myapplication.ui.adapters.BaseRecyclerViewAdapter;
import com.example.myapplication.ui.adapters.PlaylistRecyclerViewAdapter;
import com.example.myapplication.ui.adapters.models.BaseRecyclerViewItem;
import com.example.myapplication.ui.adapters.models.PlaylistRecyclerViewItem;
import com.example.myapplication.ui.adapters.models.SongRecyclerViewItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentHome extends Fragment {

    private RecyclerView mPlaylistRecyclerView;
    private PlaylistRecyclerViewAdapter mPlaylistAdapter;
    private GridLayoutManagerExtended m_vGridLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khởi tạo RecyclerView
        mPlaylistRecyclerView = view.findViewById(R.id.playlistRecyclerView);
        mPlaylistRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadPlaylistsFromApi();


    }

    private void loadPlaylistsFromApi() {
        // Gọi API để lấy danh sách các playlist
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://music-api-demo28022.onrender.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Playlist>> call = apiService.getPlaylists();

        call.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                System.out.println("load playlists");
                List<Playlist> playlists = response.body();
                List<BaseRecyclerViewItem> items = new ArrayList<>();

                for (Playlist playlist : playlists) {
                    items.add(new PlaylistRecyclerViewItem(playlist));
                }

                // Khởi tạo Adapter với danh sách playlist, đặt loại hiển thị ban đầu là LIST
                mPlaylistAdapter = new PlaylistRecyclerViewAdapter(items);
                mPlaylistAdapter.setAdapterViewType(BaseRecyclerViewAdapter.ViewType.LIST);

                // Đặt Adapter cho RecyclerView
                mPlaylistRecyclerView.setAdapter(mPlaylistAdapter);

                // Thêm padding cho RecyclerView để tránh che phủ bởi thanh điều hướng và thanh điều khiển nhạc
                int panelHeights = getResources().getDimensionPixelSize(R.dimen.navigation_bar_height) + getResources().getDimensionPixelSize(R.dimen.mediaplayerbar_height);
                mPlaylistRecyclerView.setPadding(0, 0, 0, panelHeights);
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable throwable) {
                System.out.println("Failed to load playlists");
            }
        });
    }

//    private void setAdapterViewType(BaseRecyclerViewAdapter.ViewType viewType) {
//        this.mPlaylistAdapter.setAdapterViewType(viewType);
//        int rowCount = (viewType == BaseRecyclerViewAdapter.ViewType.LIST) ? 1 : 3;
//
//        // Kiểm tra xem m_vGridLayout đã được khởi tạo chưa
//        if (m_vGridLayout == null) {
//            // Nếu không, tạo một GridLayoutManager mới và đặt nó cho RecyclerView
//            this.m_vGridLayout = new GridLayoutManagerExtended(getContext(), rowCount);
//            this.mPlaylistRecyclerView.setLayoutManager(this.m_vGridLayout);
//        } else {
//            // Nếu đã có, chỉ cần cập nhật số cột của GridLayoutManager hiện tại
//            this.m_vGridLayout.setSpanCount(rowCount);
//        }
//
//        // Thông báo cho Adapter rằng dữ liệu đã thay đổi
//        this.mPlaylistAdapter.notifyDataSetChanged();
//    }
}
