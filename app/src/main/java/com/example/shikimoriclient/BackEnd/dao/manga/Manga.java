package com.example.shikimoriclient.BackEnd.dao.manga;

import com.example.shikimoriclient.BackEnd.dao.Genre;
import com.example.shikimoriclient.BackEnd.dao.ScoreRate;
import com.example.shikimoriclient.BackEnd.dao.StatusRate;
import com.example.shikimoriclient.BackEnd.dao.UserRate;
import com.example.shikimoriclient.BackEnd.dao.anime.AnimeRating;
import com.example.shikimoriclient.BackEnd.dao.anime.Screenshot;
import com.example.shikimoriclient.BackEnd.dao.anime.Studio;
import com.example.shikimoriclient.BackEnd.dao.anime.Video;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Manga extends MangaSimple implements Serializable {
    private String[] english;
    private String[] japanese;
    private String[] synonyms;
    @SerializedName("license_name_ru")
    private String licenseNameRu;
    private String score;
    private Genre[] genres;
    private String description;
    @SerializedName("description_html")
    private String descriptionHtml;
    @SerializedName("description_source")
    private String descriptionSource;
    private String franchise;
    private boolean favoured;
    private boolean anons;
    private boolean ongoing;
    @SerializedName("thread_id")
    private int thread_id;
    @SerializedName("topic_id")
    private int topic_id;
    @SerializedName("myanimelist_id")
    private int myanimelist_id;
    @SerializedName("rates_scores_stats")
    private ScoreRate[] ratesScoresStats;
    @SerializedName("rates_statuses_stats")
    private StatusRate[] ratesStatusesStats;
    @SerializedName("user_rate")
    private UserRate userRate;
    private Publisher[] publishers;

    public String[] getEnglish() {
        return english;
    }

    public String[] getJapanese() {
        return japanese;
    }

    public String[] getSynonyms() {
        return synonyms;
    }

    public String getLicenseNameRu() {
        return licenseNameRu;
    }

    public String getScore() {
        return score;
    }

    public String getDescription() {
        if (description == null) {
            return "Нет описания";
        }
        return description;
    }

    public String getDescriptionHtml() {
        return descriptionHtml;
    }

    public String getDescriptionSource() {
        return descriptionSource;
    }

    public String getFranchise() {
        return franchise;
    }

    public boolean isFavoured() {
        return favoured;
    }

    public boolean isAnons() {
        return anons;
    }

    public boolean isOngoing() {
        return ongoing;
    }

    public int getThread_id() {
        return thread_id;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public int getMyanimelist_id() {
        return myanimelist_id;
    }

    public ScoreRate[] getRatesScoresStats() {
        return ratesScoresStats;
    }

    public StatusRate[] getRatesStatusesStats() {
        return ratesStatusesStats;
    }

    public UserRate getUserRate() {
        return userRate;
    }

    public Genre[] getGenres() {
        return genres;
    }

    public void setEnglish(String[] english) {
        this.english = english;
    }

    public void setJapanese(String[] japanese) {
        this.japanese = japanese;
    }

    public void setSynonyms(String[] synonyms) {
        this.synonyms = synonyms;
    }

    public void setLicenseNameRu(String licenseNameRu) {
        this.licenseNameRu = licenseNameRu;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setGenres(Genre[] genres) {
        this.genres = genres;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDescriptionHtml(String descriptionHtml) {
        this.descriptionHtml = descriptionHtml;
    }

    public void setDescriptionSource(String descriptionSource) {
        this.descriptionSource = descriptionSource;
    }

    public void setFranchise(String franchise) {
        this.franchise = franchise;
    }

    public void setFavoured(boolean favoured) {
        this.favoured = favoured;
    }

    public void setAnons(boolean anons) {
        this.anons = anons;
    }

    public void setOngoing(boolean ongoing) {
        this.ongoing = ongoing;
    }

    public void setThread_id(int thread_id) {
        this.thread_id = thread_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public void setMyanimelist_id(int myanimelist_id) {
        this.myanimelist_id = myanimelist_id;
    }

    public void setRatesScoresStats(ScoreRate[] ratesScoresStats) {
        this.ratesScoresStats = ratesScoresStats;
    }

    public void setRatesStatusesStats(StatusRate[] ratesStatusesStats) {
        this.ratesStatusesStats = ratesStatusesStats;
    }

    public void setUserRate(UserRate userRate) {
        this.userRate = userRate;
    }

    public void setPublishers(Publisher[] publishers) {
        this.publishers = publishers;
    }
}
