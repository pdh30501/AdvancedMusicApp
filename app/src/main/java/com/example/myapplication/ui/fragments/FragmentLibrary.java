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

import com.example.mediaplayer.LibraryManager;
import com.example.mediaplayer.model.Song;
import com.example.myapplication.R;
import com.example.myapplication.ui.adapters.LibraryRecyclerViewAdapter;
import com.example.myapplication.ui.adapters.models.BaseRecyclerViewItem;
import com.example.myapplication.ui.adapters.models.SongRecyclerViewItem;

import java.util.ArrayList;
import java.util.List;

public class FragmentLibrary extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.Library_recyclerView);

        int panalheigjts = getResources().getDimensionPixelSize(R.dimen.navigation_bar_height) + getResources().getDimensionPixelSize(R.dimen.mediaplayerbar_height);
        recyclerView.setPadding(0,0, 0, panalheigjts);

        List<Song> songs = LibraryManager.getSongs(getContext());
        List<BaseRecyclerViewItem> items = new ArrayList<>();

        for(Song song : songs)
        {
            items.add(new SongRecyclerViewItem(song));
        }

        LibraryRecyclerViewAdapter adapter = new LibraryRecyclerViewAdapter(items);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
