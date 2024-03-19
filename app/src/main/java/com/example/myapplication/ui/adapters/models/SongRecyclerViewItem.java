package com.example.myapplication.ui.adapters.models;

import com.example.mediaplayer.model.Song;

public class SongRecyclerViewItem extends  BaseRecyclerViewItem{

    private Song m_vItem;

    public SongRecyclerViewItem(Song song) {

        super(song.getTile(), ItemType.SONG);

        this.m_vItem = song;
    }

    public  String getFilePath()
    {
        return this.m_vItem.getData();
    }

}
