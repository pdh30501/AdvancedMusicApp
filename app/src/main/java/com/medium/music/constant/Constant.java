package com.medium.music.constant;

public class Constant {
    // ZaloPay
    public static final int APP_ID = 554;
    public static final String MAC_KEY = "8NdU5pG5R2spGHGhyO99HN1OhD8IQJBn";
    public static final String URL_CREATE_ORDER = "https://sandbox.zalopay.com.vn/v001/tpe/createorder";

    // Firebase url
    public static final String FIREBASE_URL = "https://music-android-ae620-default-rtdb.asia-southeast1.firebasedatabase.app";


    // Max count
    public static final int MAX_COUNT_BANNER = 5;
    public static final int MAX_COUNT_POPULAR = 4;
    public static final int MAX_COUNT_LATEST = 4;

    public static final String ADMIN_EMAIL_FORMAT = "@admin.com";
    public static final String KEY_INTENT_SONG_OBJECT = "song_object";

    // Music actions
    public static final int PLAY = 0;
    public static final int PAUSE = 1;
    public static final int NEXT = 2;
    public static final int PREVIOUS = 3;
    public static final int RESUME = 4;
    public static final int CANNEL_NOTIFICATION = 5;
    public static final String MUSIC_ACTION = "musicAction";
    public static final String SONG_POSITION = "songPosition";
    public static final String CHANGE_LISTENER = "change_listener";
}