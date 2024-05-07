package com.medium.music.listener;

import com.medium.music.model.Song;

public interface IOnManagerSongListener {
    void onClickUpdateSong(Song song);
    void onClickDeleteSong(Song song);
}
