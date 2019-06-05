package com.example.shikimoriclient.BackEnd.dao.anime;

import com.example.shikimoriclient.BackEnd.dao.Genre;
import com.example.shikimoriclient.BackEnd.dao.ScoreRate;
import com.example.shikimoriclient.BackEnd.dao.StatusRate;
import com.example.shikimoriclient.BackEnd.dao.UserRate;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Anime extends AnimeSimple implements Serializable {
    private AnimeRating rating;
    private String[] english;
    private String[] japanese;
    private String[] synonyms;
    @SerializedName("license_name_ru")
    private String licenseNameRu;
    private int duration;
    private String score;
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
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("next_episode_at")
    private String nextEpisodeAt;
    private Genre[] genres;
    private Studio[] studios;
    private Video[] videos;
    private Screenshot[] screenshots;
    @SerializedName("user_rate")
    private UserRate userRate;

    public AnimeRating getRating() {
        return rating;
    }

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

    public int getDuration() {
        return duration;
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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getNextEpisodeAt() {
        return nextEpisodeAt;
    }

    public Genre[] getGenres() {
        return genres;
    }

    public Studio[] getStudios() {
        return studios;
    }

    public Video[] getVideos() {
        return videos;
    }

    public Screenshot[] getScreenshots() {
        return screenshots;
    }

    public UserRate getUserRate() {
        return userRate;
    }

    public void setRating(AnimeRating rating) {
        this.rating = rating;
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

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setScore(String score) {
        this.score = score;
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

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setNextEpisodeAt(String nextEpisodeAt) {
        this.nextEpisodeAt = nextEpisodeAt;
    }

    public void setGenres(Genre[] genres) {
        this.genres = genres;
    }

    public void setStudios(Studio[] studios) {
        this.studios = studios;
    }

    public void setVideos(Video[] videos) {
        this.videos = videos;
    }

    public void setScreenshots(Screenshot[] screenshots) {
        this.screenshots = screenshots;
    }

    public void setUserRate(UserRate userRate) {
        this.userRate = userRate;
    }
}
