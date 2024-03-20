package com.example.mediaplayer.interfaces;

import java.util.List;

public interface IPlayerCallback {

    void onClickPlay(int queueIndex, List<Integer> queue);

    void onClickPlayIndex(int index);

    void onClickPlayNext();

    void onClickPlayPrev();

    void onClickPlayPause();

    void onSetSeekBar(int position);

    void onUpdateQueue(List<Integer> queue, int queueIndex);

    void onDestroy();


}
