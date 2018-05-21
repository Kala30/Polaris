package com.polaris.polaris;

public class Movie {
    public String title;
    public String released;
    public String rated;
    public String genre;
    public String plot;
    public String rtScore;
    public String imdbScore;
    public String metaScore;
    public String posterURL;
    public int imdbId;

    public Movie(String title, String year) {
        this.title = title;
        this.released = year;
    }
}
