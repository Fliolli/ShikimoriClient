package com.example.shikimoriclient.BackEnd.api.manga;

import android.content.Context;
import android.content.Intent;

import com.example.shikimoriclient.BackEnd.api.Api;
import com.example.shikimoriclient.BackEnd.api.ItemHandler;
import com.example.shikimoriclient.BackEnd.dao.anime.Anime;
import com.example.shikimoriclient.BackEnd.dao.manga.Manga;
import com.example.shikimoriclient.DetailInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shikimoriclient.MainActivity.loggedIn;
import static com.example.shikimoriclient.UserInfoHandler.ACCESS_TOKEN;

public class MangaItemHandler implements ItemHandler {

    @Override
    public void findById(int id, Context context) {
        Mangas api = Api.getMangas();
        Call<Manga> call;
        if (!loggedIn) {
            call = api.getManga(id,null);
        } else {
            call = api.getManga(id, ACCESS_TOKEN);
        }
        call.enqueue(new Callback<Manga>() {
            @Override
            public void onResponse(Call<Manga> call, Response<Manga> response) {
                Intent intent = new Intent(context, DetailInfo.class);
                intent.putExtra("Item", response.body());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

            @Override
            public void onFailure(Call<Manga> call, Throwable t) {

            }
        });
    }
}
