package com.example.shikimoriclient.BackEnd.dao.manga;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public enum MangaKind implements Serializable {
    @SerializedName("manga")
    MANGA("Манга"),
    @SerializedName("manhwa")
    MANHWA("Манхва"),
    @SerializedName("novel")
    NOVEL("Новелла"),
    @SerializedName("manhua")
    MANHUA("Манхуа"),
    @SerializedName("one_shot")
    ONE_SHOT("Ваншот"),
    @SerializedName("doujin")
    DOUJIN("Додзинси");

    private String kind;

    MangaKind(String kind) {
        this.kind = kind;
    }

    @NonNull
    @Override
    public String toString() {
        return kind;
    }
}
