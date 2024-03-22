package com.example.mediaplayer.interfaces;

import com.example.mediaplayer.PlaybackManager;
import com.example.mediaplayer.model.Song;

import java.util.List;

public interface IPlayerCallback {

    void onClickPlay(int queueIndex, List<Integer> queue);

    void onClickPlayIndex(int index);

    void onClickPlayNext();

    void onClickPlayPrev();

    void onClickPlayPause();

    void onSetSeekBar(int position);


    void onClickStop();

    void onClickPlayPrevious();

    void onSetSeekbar(int position);

    void onSetRepeatType(@PlaybackManager.RepeatType int repeatType);

    void onUpdateQueue(List<Song> queue, int queueIndex);

    void onDestroy();


}
