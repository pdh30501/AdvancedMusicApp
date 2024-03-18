package com.example.mediaplayer;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaDescription;
import android.media.MediaMetadata;

import com.example.mediaplayer.model.Song;

public class LibraryManager {


    public static MediaDescription getMediaDescription (Song song) {
        if (song==null){
            return null;
        }
        MediaDescription.Builder builder=new MediaDescription.Builder();

        builder.setMediaId(String.valueOf(song.getId()));
        builder.setIconBitmap(getSongArt(song));
        builder.setTitle(song.getTile());
        builder.setSubtitle(song.getArtistName());
        builder.setDescription(song.getTile());

        return builder.build();
    }
    public static MediaMetadata getMediaMetadata (Song song){
         if (song==null){
             return null;
         }
         MediaMetadata.Builder builder=new MediaMetadata.Builder();
         builder.putString(MediaMetadata.METADATA_KEY_MEDIA_ID, String.valueOf(song.getId()));
         builder.putString(MediaMetadata.METADATA_KEY_ALBUM,song.getAlbumName());
         builder.putString(MediaMetadata.METADATA_KEY_ARTIST,song.getArtistName());
         builder.putString(MediaMetadata.METADATA_KEY_TITLE,song.getTile());
         builder.putLong(MediaMetadata.METADATA_KEY_DURATION,song.getDuration());

         Bitmap songArt=getSongArt(song);
         if (songArt !=null){
             builder.putBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART,songArt);
         }
         return builder.build();

    }

    public static long getCursorLongByIndex(Cursor cursor,String columnName){
        int index= cursor.getColumnIndex(columnName);
        return (index>1)? cursor.getLong(index) : -1L;
    }
    private static String getCursorStringByIndex(Cursor cursor,String columnName){
        int index=cursor.getColumnIndex(columnName);
        return (index>1)? cursor.getString(index) : "";
    }
    private static Bitmap getSongArt(Song song){
        return null;
    }
}
