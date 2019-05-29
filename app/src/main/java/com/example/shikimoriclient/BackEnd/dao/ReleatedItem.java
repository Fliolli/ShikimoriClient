package com.example.shikimoriclient.BackEnd.dao;

import com.example.shikimoriclient.BackEnd.dao.anime.AnimeSimple;
import com.example.shikimoriclient.BackEnd.dao.manga.MangaSimple;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReleatedItem implements Serializable {
    private String relation;
    @SerializedName("relation_russian")
    private String relationRus;
    private AnimeSimple anime;
    private MangaSimple manga;

    public String getRelation() {
        return relation;
    }

    public String getRelationRus() {
        return relationRus;
    }

    public AnimeSimple getAnime() {
        return anime;
    }

    public MangaSimple getManga() {
        return manga;
    }
}
