package com.example.shikimoriclient.BackEnd.api.ranobe;

import com.example.shikimoriclient.BackEnd.dao.ranobe.Ranobe;
import com.example.shikimoriclient.BackEnd.dao.ranobe.RanobeSimple;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface Ranobes {

    @GET("/api/ranobe")
    Call<List<RanobeSimple>> getList(@QueryMap Map<String, String> params, @Header("Authorization") String authorization);

    @GET("/api/ranobe/{id}")
    Call<Ranobe> getRanobe(@Path("id") int id, @Header("Authorization") String authorization);
}
