package com.example.shikimoriclient.BackEnd.api.ranobe;

import android.content.Context;
import android.content.Intent;

import com.example.shikimoriclient.BackEnd.api.Api;
import com.example.shikimoriclient.BackEnd.api.ItemHandler;
import com.example.shikimoriclient.BackEnd.dao.ranobe.Ranobe;
import com.example.shikimoriclient.DetailInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shikimoriclient.MainActivity.loggedIn;
import static com.example.shikimoriclient.BackEnd.data.UserInfoHandler.ACCESS_TOKEN;

public class RanobeItemHandler implements ItemHandler {

    @Override
    public void findById(int id, Context context) {
        Ranobes api = Api.getRanobe();
        Call<Ranobe> call;
        if (!loggedIn) {
            call = api.getRanobe(id, null);
        } else {
            call = api.getRanobe(id, ACCESS_TOKEN);
        }
        call.enqueue(new Callback<Ranobe>() {
            @Override
            public void onResponse(Call<Ranobe> call, Response<Ranobe> response) {
                Intent intent = new Intent(context, DetailInfo.class);
                intent.putExtra("Item", response.body());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

            @Override
            public void onFailure(Call<Ranobe> call, Throwable t) {

            }
        });
    }
}
