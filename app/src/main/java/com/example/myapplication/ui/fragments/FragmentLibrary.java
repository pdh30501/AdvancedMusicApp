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
        this.m_vRootView=inflater.inflate(R.layout.fragment_library,container,false);

        return this.m_vRootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.m_vLibraryRecyclerView = findViewById(R.id.Library_recyclerView);

        int panalheigjts = getResources().getDimensionPixelSize(R.dimen.navigation_bar_height) + getResources().getDimensionPixelSize(R.dimen.mediaplayerbar_height);
        this.m_vLibraryRecyclerView.setPadding(0,0, 0, panalheigjts);

        List<Song> songs = LibraryManager.getSongs(getContext());
        List<BaseRecyclerViewItem> items = new ArrayList<>();

        for(Song song : songs)
        {
            items.add(new SongRecyclerViewItem(song));
        }

        LibraryRecyclerViewAdapter adapter = new LibraryRecyclerViewAdapter(items);

        this.m_vLibraryAdapter = new LibraryRecyclerViewAdapter(items);
        this.m_vLibraryAdapter.setHasStableIds(true);
        setAdapterViewType(BaseRecyclerViewAdapter.ViewType.GRID);
//        this.m_vLibraryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.m_vLibraryRecyclerView.setAdapter(this.m_vLibraryAdapter);

        FloatingActionButton btn = findViewById(R.id.btn_test_layout);
        btn.setOnClickListener(v -> {
            setAdapterViewType((
                    this.m_vLibraryAdapter.getViewType() == BaseRecyclerViewAdapter.ViewType.GRID) ?
                    BaseRecyclerViewAdapter.ViewType.LIST : BaseRecyclerViewAdapter.ViewType.GRID);
        });
    }
    private void setAdapterViewType(BaseRecyclerViewAdapter.ViewType viewType) {
        this.m_vLibraryAdapter.setAdapterViewType(viewType);
        int rowCount = (viewType == BaseRecyclerViewAdapter.ViewType.LIST) ? 1 : 3;

        if(m_vGridLayout == null) {
            this.m_vGridLayout = new GridLayoutManagerExtended(getContext(), rowCount);
            this.m_vLibraryRecyclerView.setLayoutManager(this.m_vGridLayout);
        }
        else {
            this.m_vGridLayout.setSpanCount(rowCount);
            this.m_vLibraryAdapter.notifyDataSetChanged();

        }

        this.m_vLibraryAdapter.setAdapterViewType(viewType);
    }
    public <T extends View> T findViewById(@IdRes int id){
        return this.m_vRootView.findViewById(id);
    }
}
