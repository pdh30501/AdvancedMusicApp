package com.example.mediaplayer.interfaces;

import com.example.mediaplayer.model.Playlist;
import com.example.mediaplayer.model.Track;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("playlists")
    Call<List<Playlist>> getPlaylists();

    @GET("playlist/{id}")
    Call<Playlist> getPlaylist(@Path("id") String id);

//    @GET("playlist/{id}/tracks")
//    Call<List<Track>> getTracks(@Path("id") String id);
}
