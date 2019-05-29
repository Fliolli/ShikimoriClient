package com.example.shikimoriclient.FrontEnd.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shikimoriclient.BackEnd.api.Api;
import com.example.shikimoriclient.BackEnd.dao.manga.Manga;
import com.example.shikimoriclient.R;
import com.squareup.picasso.Picasso;

public class RanobeOrMangaDetails extends Fragment {
    private TextView nameText;
    private TextView rusNameText;
    private TextView scoreText;
    private TextView typeText;
    private TextView tomsCountText;
    private TextView chaptersCountText;
    private TextView statusText;
    private TextView genresText;
    private TextView desctiptionText;
    private ImageView mangaImage;

    private static Bundle instanceBundle = new Bundle();

    private Manga manga;

    public static RanobeOrMangaDetails newInstance(Manga manga) {
        instanceBundle.putSerializable("Manga", manga);
        return new RanobeOrMangaDetails();
    }

    public RanobeOrMangaDetails() {
        manga = (Manga) instanceBundle.getSerializable("Manga");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_ranobe_or_manga_details, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeComp(view);
    }

    private void initializeComp(View view) {
        nameText = view.findViewById(R.id.mangaNameOrig);
        rusNameText = view.findViewById(R.id.manga_rus_name);
        scoreText = view.findViewById(R.id.mangaScore);
        typeText = view.findViewById(R.id.mangaType);
        tomsCountText = view.findViewById(R.id.mangaTomsCount);
        chaptersCountText = view.findViewById(R.id.mangaChapters);
        statusText = view.findViewById(R.id.mangaStatus);
        genresText = view.findViewById(R.id.mangaGenres);
        desctiptionText = view.findViewById(R.id.mangaDescription);
        mangaImage = getLayoutInflater().inflate(R.layout.detail_info, null).findViewById(R.id.backdrop);
        setCompConfiguration();
        fillComp();
        setListeners();
    }

    @SuppressLint("SetTextI18n")
    private void fillComp() {
        nameText.setText(manga.getName());
        rusNameText.setText(manga.getRussian());
        scoreText.setText(manga.getScore());
        typeText.setText(manga.getKind().toString());
        if (manga.getVolumes() != 0) {
            tomsCountText.setText(Integer.toString(manga.getVolumes()));
        } else {
            tomsCountText.setText("-");
        }
        if (manga.getChapters() != 0) {
            chaptersCountText.setText(Integer.toString(manga.getChapters()));
        } else {
            chaptersCountText.setText("-");
        }
        statusText.setText(manga.getStatus().toString());
        StringBuilder genres = new StringBuilder();
        for (int i = 0; i < manga.getGenres().length; i++) {
            genres.append(manga.getGenres()[i].getRussian()).append(", ");
        }
        genres.delete(genres.length() - 2, genres.length());
        genresText.setText(genres.toString());
        if (manga.getDescription() != null) {
            desctiptionText.setText(manga.getDescription().replaceAll("\\[.+]", ""));
        } else {
            desctiptionText.setText("Нет описания");
        }
    }

    private void setCompConfiguration() {

    }

    private void setListeners() {

    }
}
