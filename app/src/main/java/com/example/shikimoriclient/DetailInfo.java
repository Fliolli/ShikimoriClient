package com.example.shikimoriclient;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v4.app.FragmentManager;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.shikimoriclient.BackEnd.api.Api;
import com.example.shikimoriclient.BackEnd.api.user.Users;
import com.example.shikimoriclient.BackEnd.dao.ItemSimple;
import com.example.shikimoriclient.BackEnd.dao.UserRate;
import com.example.shikimoriclient.BackEnd.dao.anime.Anime;
import com.example.shikimoriclient.BackEnd.dao.manga.Manga;
import com.example.shikimoriclient.BackEnd.data.UserInfoHandler;
import com.example.shikimoriclient.FrontEnd.fragments.AnimeDetails;
import com.example.shikimoriclient.FrontEnd.fragments.RanobeOrMangaDetails;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailInfo extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    private FragmentManager myFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private boolean animeType;
    private PopupMenu popupMenu;
    ItemSimple itemSimple;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_info);
        initializeComp();
    }

    private void initializeComp() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        floatingActionButton = findViewById(R.id.menuButton);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        itemSimple = (ItemSimple) getIntent().getSerializableExtra("Item");
        animeType = itemSimple instanceof Anime;
        popupMenu = new PopupMenu(this, floatingActionButton);
        MenuInflater inflater = popupMenu.getMenuInflater();
        if (animeType) {
            inflater.inflate(R.menu.menu_anime_details, popupMenu.getMenu());
        } else {
            inflater.inflate(R.menu.menu_ranobe_and_manga_details, popupMenu.getMenu());
        }
        myFragmentManager = getSupportFragmentManager();
        fragmentTransaction = myFragmentManager
                .beginTransaction();
        setCompConfiguration();
        fillComp();
        setListeners();
        fragmentTransaction.commit();
    }

    private void fillComp() {
        if (animeType) {
            Anime anime = (Anime) itemSimple;
            AnimeDetails animeDetails = AnimeDetails.newInstance(anime);
            fragmentTransaction.add(R.id.container, animeDetails);
            if (anime.getUserRate() != null) {
                switch (anime.getUserRate().getStatus()) {
                    case "planned": {
                        popupMenu.getMenu().getItem(3).setChecked(true);
                        break;
                    }
                    case "watching": {
                        popupMenu.getMenu().getItem(5).setChecked(true);
                        break;
                    }
                    case "rewatching": {
                        popupMenu.getMenu().getItem(4).setChecked(true);
                        break;
                    }
                    case "completed": {
                        popupMenu.getMenu().getItem(0).setChecked(true);
                        break;
                    }
                    case "on_hold": {
                        popupMenu.getMenu().getItem(2).setChecked(true);
                        break;
                    }
                    case "dropped": {
                        popupMenu.getMenu().getItem(1).setChecked(true);
                        break;
                    }
                }
            }
        } else {
            Manga manga = (Manga) itemSimple;
            RanobeOrMangaDetails ranobeOrMangaDetails = RanobeOrMangaDetails.newInstance(manga);
            fragmentTransaction.add(R.id.container, ranobeOrMangaDetails);
            if (manga.getUserRate() != null) {
                switch (manga.getUserRate().getStatus()) {
                    case "planned": {
                        popupMenu.getMenu().getItem(3).setChecked(true);
                        break;
                    }
                    case "watching": {
                        popupMenu.getMenu().getItem(5).setChecked(true);
                        break;
                    }
                    case "rewatching": {
                        popupMenu.getMenu().getItem(4).setChecked(true);
                        break;
                    }
                    case "completed": {
                        popupMenu.getMenu().getItem(0).setChecked(true);
                        break;
                    }
                    case "on_hold": {
                        popupMenu.getMenu().getItem(2).setChecked(true);
                        break;
                    }
                    case "dropped": {
                        popupMenu.getMenu().getItem(1).setChecked(true);
                        break;
                    }
                }
            }
        }
    }

    private void setCompConfiguration() {

    }

    private void setListeners() {
        floatingActionButton.setOnClickListener(v -> {
            if (MainActivity.loggedIn) {
                popupMenu.show();
            } else {
                Toast.makeText(this, "Войдите в личный кабинет для управления списками", Toast.LENGTH_LONG).show();
            }
        });
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.isChecked()) {
                if (animeType) {
                    int id = ((Anime) itemSimple).getUserRate().getId();
                    Users api = Api.getUser();
                    Call<Void> call = api.deleteUserRate(id, UserInfoHandler.ACCESS_TOKEN);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(DetailInfo.this, "Удалено из списка", Toast.LENGTH_LONG).show();
                            if (item.isChecked()) {
                                item.setChecked(false);
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                } else {
                    Users api = Api.getUser();
                    Call<Void> call = api.deleteUserRate(((Manga) itemSimple).getUserRate().getId(), UserInfoHandler.ACCESS_TOKEN);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(DetailInfo.this, "Удалено из списка", Toast.LENGTH_LONG).show();
                            if (item.isChecked()) {
                                item.setChecked(false);
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                        }
                    });
                }
                return false;
            } else {
                UserRate userRate = new UserRate();
                userRate.setTargetId(itemSimple.getId());
                userRate.setUserId(Integer.parseInt(UserInfoHandler.USER_ID));
                if (animeType) {
                    switch (item.getItemId()) {
                        case R.id.completed:
                            userRate.setStatus("completed");
                            break;
                        case R.id.dropped:
                            userRate.setStatus("dropped");
                            break;
                        case R.id.on_hold:
                            userRate.setStatus("on_hold");
                            break;
                        case R.id.plans:
                            userRate.setStatus("planned");
                            break;
                        case R.id.rewatching:
                            userRate.setStatus("rewatching");
                            break;
                        case R.id.watching:
                            userRate.setStatus("watching");
                            break;
                    }
                    userRate.setTargetType("Anime");
                } else {
                    switch (item.getItemId()) {
                        case R.id.completed:
                            userRate.setStatus("completed");
                            break;
                        case R.id.dropped:
                            userRate.setStatus("dropped");
                            break;
                        case R.id.on_hold:
                            userRate.setStatus("on_hold");
                            break;
                        case R.id.plans:
                            userRate.setStatus("planned");
                            break;
                        case R.id.rewatching:
                            userRate.setStatus("rewatching");
                            break;
                        case R.id.watching:
                            userRate.setStatus("watching");
                            break;
                    }
                    userRate.setTargetType("Manga");
                }
                Users api = Api.getUser();
                HashMap<String, Object> map = new HashMap<>();
                map.put("user_rate", userRate);
                Call<UserRate> call = api.addUserRate(map, UserInfoHandler.ACCESS_TOKEN);
                call.enqueue(new Callback<UserRate>() {
                    @Override
                    public void onResponse(Call<UserRate> call, Response<UserRate> response) {
                        if (response.body() != null) {
                            if (animeType) {
                                ((Anime) itemSimple).setUserRate(response.body());
                            } else {
                                ((Manga) itemSimple).setUserRate(response.body());
                            }
                            item.setChecked(true);
                            Toast.makeText(DetailInfo.this, "Добавлено в список", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(DetailInfo.this, "Что-то пошло не так", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserRate> call, Throwable t) {
                    }
                });
            }
            return false;
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
