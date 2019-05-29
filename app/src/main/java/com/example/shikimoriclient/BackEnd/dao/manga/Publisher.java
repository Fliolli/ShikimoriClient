package com.example.shikimoriclient.BackEnd.dao.manga;

import java.io.Serializable;

public class Publisher implements Serializable {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
