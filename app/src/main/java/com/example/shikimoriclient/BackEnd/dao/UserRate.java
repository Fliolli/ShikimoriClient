package com.example.shikimoriclient.BackEnd.dao;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserRate implements Serializable {
    private int id;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("target_id")
    private int targetId;
    @SerializedName("target_type")
    private String targetType;
    private int score;
    private String status;
    private String text;
    private int episodes;
    private int chapters;
    private int volumes;
    @SerializedName("text_html")
    private String textHtml;
    private int rewatches;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public void setChapters(int chapters) {
        this.chapters = chapters;
    }

    public void setVolumes(int volumes) {
        this.volumes = volumes;
    }

    public void setTextHtml(String textHtml) {
        this.textHtml = textHtml;
    }

    public void setRewatches(int rewatches) {
        this.rewatches = rewatches;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getTargetId() {
        return targetId;
    }

    public String getTargetType() {
        return targetType;
    }

    public int getScore() {
        return score;
    }

    public String getStatus() {
        return status;
    }

    public String getText() {
        return text;
    }

    public int getEpisodes() {
        return episodes;
    }

    public int getChapters() {
        return chapters;
    }

    public int getVolumes() {
        return volumes;
    }

    public String getTextHtml() {
        return textHtml;
    }

    public int getRewatches() {
        return rewatches;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
