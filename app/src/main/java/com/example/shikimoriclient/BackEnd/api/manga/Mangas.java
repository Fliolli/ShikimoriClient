package com.example.shikimoriclient.BackEnd.api.manga;

import com.example.shikimoriclient.BackEnd.dao.manga.Manga;
import com.example.shikimoriclient.BackEnd.dao.manga.MangaSimple;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface Mangas {

    @GET("/api/mangas")
    Call<List<MangaSimple>> getList(@QueryMap Map<String, String> params, @Header("Authorization") String authorization);

    @GET("/api/mangas/{id}")
    Call<Manga> getManga(@Path("id") int id, @Header("Authorization") String authorization);
}
