package com.example.moviesearch.model;

public class MovieSummary {

    private String imdbId;
    private String title;
    private String year;
    private String type;
    private String posterUrl;

    public MovieSummary() {
    }

    public MovieSummary(String imdbId, String title, String year, String type, String posterUrl) {
        this.imdbId = imdbId;
        this.title = title;
        this.year = year;
        this.type = type;
        this.posterUrl = posterUrl;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}

