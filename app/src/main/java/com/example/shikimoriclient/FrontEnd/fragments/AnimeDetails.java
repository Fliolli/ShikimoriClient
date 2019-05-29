package com.example.shikimoriclient.FrontEnd.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shikimoriclient.BackEnd.api.Api;
import com.example.shikimoriclient.BackEnd.api.anime.Animes;
import com.example.shikimoriclient.BackEnd.dao.ItemSimple;
import com.example.shikimoriclient.BackEnd.dao.ReleatedItem;
import com.example.shikimoriclient.BackEnd.dao.anime.Anime;
import com.example.shikimoriclient.BackEnd.dao.anime.AnimeSimple;
import com.example.shikimoriclient.FrontEnd.adapters.RecyclerAdapter;
import com.example.shikimoriclient.R;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class AnimeDetails extends Fragment {
    private TextView scoreText;
    private TextView nameOriginText;
    private TextView nameRusText;
    private TextView typeText;
    private TextView episodeCountText;
    private TextView durationText;
    private TextView statusText;
    private TextView ratingText;
    private TextView genresText;
    private TextView desctiptionText;
    private ImageView animeImage;

  /*  private RecyclerView similarRecyclerView;
    private RecyclerAdapter similarRecyclerAdapter;

    private RecyclerView relatedRecyclerView;
    private RecyclerAdapter relatedRecyclerAdapter;*/

    private static Bundle instanceBundle = new Bundle();

    private Anime anime;

    public static AnimeDetails newInstance(Anime anime) {
        instanceBundle.putSerializable("Anime", anime);
        return new AnimeDetails();
    }

    public AnimeDetails() {
        anime = (Anime) instanceBundle.getSerializable("Anime");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_anime_details, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeComp(view);
    }

    private void initializeComp(View view) {
        scoreText = view.findViewById(R.id.animeScore);
        nameRusText = view.findViewById(R.id.anime_rus_name);
        nameOriginText = view.findViewById(R.id.animeNameOrig);
        typeText = view.findViewById(R.id.animeType);
        episodeCountText = view.findViewById(R.id.animeEpisodeCount);
        durationText = view.findViewById(R.id.animeDuration);
        statusText = view.findViewById(R.id.animeStatus);
        ratingText = view.findViewById(R.id.animeRating);
        genresText = view.findViewById(R.id.animeGenres);
        desctiptionText = view.findViewById(R.id.animeDescription);
        animeImage = getLayoutInflater().inflate(R.layout.detail_info, null).findViewById(R.id.backdrop);
       /* LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        relatedRecyclerView = view.findViewById(R.id.recyclerView1);
        // relatedRecyclerView.setLayoutManager(layoutManager);
        similarRecyclerView = view.findViewById(R.id.recyclerView2);
        //   similarRecyclerView.setLayoutManager(layoutManager);*/
        setCompConfiguration();
        fillComp();
        setListeners();
    }

    @SuppressLint("SetTextI18n")
    private void fillComp() {
        scoreText.setText(anime.getScore());
        nameRusText.setText(anime.getRussian());
        nameOriginText.setText(anime.getName());
        typeText.setText(anime.getKind().toString());
        episodeCountText.setText(Integer.toString(anime.getEpisodes()));
        durationText.setText(Integer.toString(anime.getDuration()));
        statusText.setText(anime.getStatus().toString());
        ratingText.setText(anime.getRating().toString());
        StringBuilder genres = new StringBuilder();
        for (int i = 0; i < anime.getGenres().length; i++) {
            genres.append(anime.getGenres()[i].getRussian()).append(", ");
        }
        genres.delete(genres.length() - 2, genres.length());
        genresText.setText(genres.toString());
        if (anime.getDescription() != null) {
            desctiptionText.setText(anime.getDescription().replaceAll("\\[.+]", ""));
        } else {
            desctiptionText.setText("Нет описания");
        }
        Picasso.get().load(Api.baseURL + anime.getImage().getOriginal()).into(animeImage);
    }

    private void setCompConfiguration() {
       /* Animes api = Api.getAnimes();
        Call<List<AnimeSimple>> call;
        call = api.getSimilar(anime.getId());
        call.enqueue(new Callback<List<AnimeSimple>>() {
            @Override
            public void onResponse(Call<List<AnimeSimple>> call, Response<List<AnimeSimple>> response) {
                similarRecyclerAdapter = new RecyclerAdapter(response.body());
                similarRecyclerView.setAdapter(similarRecyclerAdapter);
            }

            @Override
            public void onFailure(Call<List<AnimeSimple>> call, Throwable t) {
            }
        });

        Call<List<ReleatedItem>> call1;
        call1 = api.getRelated(anime.getId());
        call1.enqueue(new Callback<List<ReleatedItem>>() {
            @Override
            public void onResponse(Call<List<ReleatedItem>> call, Response<List<ReleatedItem>> response) {
                List<ItemSimple> releatedItems = new LinkedList<>();
                for (ReleatedItem releatedItem : response.body()) {
                    if (releatedItem.getAnime() != null) {
                        releatedItems.add(releatedItem.getAnime());
                    } else {
                        releatedItems.add(releatedItem.getManga());
                    }
                }
                relatedRecyclerAdapter = new RecyclerAdapter(releatedItems);
                relatedRecyclerView.setAdapter(relatedRecyclerAdapter);
            }

            @Override
            public void onFailure(Call<List<ReleatedItem>> call, Throwable t) {
            }
        });*/
    }

    private void setListeners() {

    }
}
