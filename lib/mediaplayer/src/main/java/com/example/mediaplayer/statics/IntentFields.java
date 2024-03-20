package com.example.mediaplayer.statics;

public class IntentFields {
//    Hằng số thông báo phương tiện

    public static final String ACTION_NEXT = "com.example.samplemusicplayer.mediaplayer.NEXT";
    public static final String ACTION_PREV = "com.example.samplemusicplayer.mediaplayer.PREV";
    public static final String ACTION_PAUSE = "com.example.samplemusicplayer.mediaplayer.PAUSE";
    public static final String ACTION_ID = "com.example.samplemusicplayer.mediaplayer.ID";

//    Extra intent constants

    public static final String EXTRA_REPEAT_STATE = "REPEAT_STATE";
    public static final String EXTRA_TRACKS_QUEUE = "TRACKS_QUEUE";
    public static final String EXTRA_TRACK_ID = "TRACK_ID";
    public static final String EXTRA_TRACK_INDEX = "TRACK_INDEX";
    public static final String EXTRA_SEEK_BAR_POSITION = "SEEK_BAR_POSITION";

//    media players service constants

    public static final String INTENT_ADD_TRACK_NEXT = "com.example.samplemusicplayer.service.MediaPlayerService.ADD_TRACK_NEXT";
    public static final String INTENT_CHANGE_REPEAT = "com.example.samplemusicplayer.service.MediaPlayerService.CHANGE_REPEAT";
    public static final String INTENT_PLAY = "com.example.samplemusicplayer.service.MediaPlayerService.PLAY";
    public static final String INTENT_PLAY_INDEX = "com.example.samplemusicplayer.service.MediaPlayerService.PLAY_INDEX";
    public static final String INTENT_PLAY_PREV = "com.example.samplemusicplayer.service.MediaPlayerService.PLAY_PREV";
    public static final String INTENT_PLAY_PAUSE = "com.example.samplemusicplayer.service.MediaPlayerService.PLAY_PAUSE";
    public static final String INTENT_PLAY_NEXT = "com.example.samplemusicplayer.service.MediaPlayerService.PLAY_NEXT";
    public static final String INTENT_SET_SEEKBAR = "com.example.samplemusicplayer.service.MediaPlayerService.SET_SEEKBAR";
    public static final String INTENT_UPDATE_QUEUE = "com.example.samplemusicplayer.service.MediaPlayerService.UPDATE_QUEUE";

}
