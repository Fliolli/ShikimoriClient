package com.example.shikimoriclient.BackEnd.dao.manga;

import com.example.shikimoriclient.BackEnd.dao.ItemSimple;

import java.io.Serializable;

public class MangaSimple  extends ItemSimple implements Serializable {

    private MangaKind kind;
    private int volumes;
    private int chapters;

    public MangaKind getKind() {
        return kind;
    }

    public int getVolumes() {
        return volumes;
    }

    public int getChapters() {
        return chapters;
    }
}
