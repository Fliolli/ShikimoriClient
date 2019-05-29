package com.example.shikimoriclient.BackEnd.api.anime;

import com.example.shikimoriclient.BackEnd.dao.ReleatedItem;
import com.example.shikimoriclient.BackEnd.dao.anime.Anime;
import com.example.shikimoriclient.BackEnd.dao.anime.AnimeSimple;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface Animes {

    @GET("/api/animes")
    Call<List<AnimeSimple>> getList(@QueryMap Map<String, String> params, @Header("Authorization") String authorization);

    @GET("/api/animes/{id}")
    Call<Anime> getAnime(@Path("id") int id, @Header("Authorization") String authorization);

    @GET("/api/animes/{id}/similar")
    Call<List<AnimeSimple>> getSimilar(@Path("id") int id);

    @GET("/api/animes/{id}/related")
    Call<List<ReleatedItem>> getRelated(@Path("id") int id);

}
