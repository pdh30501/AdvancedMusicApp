package com.example.mediaplayer.statics;

public class IntentFields {
//    Hằng số thông báo phương tiện

    public static final String ACTION_NEXT = "com.example.myapplication.mediaplayer.NEXT";
    public static final String ACTION_PREV = "com.example.myapplication.mediaplayer.PREV";
    public static final String ACTION_PAUSE = "com.example.myapplication.mediaplayer.PAUSE";
    public static final String CHANNEL_ID = "com.example.myapplication";

        public static final String ACTION_PLAY = "com.example.myapplication.mediaplayer.ACTION_PLAY";



//    Extra intent constants

    public static final String EXTRA_REPEAT_STATE = "REPEAT_STATE";
    public static final String EXTRA_TRACKS_LIST = "TracksList";

    public static final String EXTRA_TRACKS_ID = "TRACK_ID";
    public static final String EXTRA_TRACK_INDEX = "TRACK_INDEX";
    public static final String EXTRA_SEEK_BAR_POSITION = "TrackPosition";


//    media players service constants

    public static final String INTENT_ADD_TRACK_NEXT = "com.example.myapplication.service.MediaPlayerService.ADD_TRACK_NEXT";
    public static final String INTENT_CHANGE_REPEAT = "com.example.myapplication.service.MediaPlayerService.CHANGE_REPEAT";
    public static final String INTENT_PLAY = "com.example.myapplication.service.MediaPlayerService.PLAY";
    public static final String INTENT_STOP = "com.example.myapplication.service.MediaPlayerService.Stop";
    public static final String INTENT_PLAY_INDEX = "com.example.myapplication.service.MediaPlayerService.PLAY_INDEX";
    public static final String INTENT_PLAY_PREV = "com.example.myapplication.service.MediaPlayerService.PLAY_PREV";
    public static final String INTENT_PLAY_PAUSE = "com.example.myapplication.service.MediaPlayerService.PLAY_PAUSE";
    public static final String INTENT_PLAY_NEXT = "com.example.myapplication.service.MediaPlayerService.PLAY_NEXT";
    public static final String INTENT_SET_SEEKBAR = "com.example.myapplication.service.MediaPlayerService.SET_SEEKBAR";
    public static final String INTENT_UPDATE_QUEUE = "com.example.myapplication.service.MediaPlayerService.UPDATE_QUEUE";

}
