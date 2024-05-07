package com.medium.music.listener;

import com.medium.music.model.Song;

public interface IOnClickSongItemListener {
    void onClickItemSong(Song song);
    void onClickFavoriteSong(Song song, boolean favorite);
}
