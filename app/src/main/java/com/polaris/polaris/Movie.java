package com.polaris.polaris;

public class Movie implements java.io.Serializable{

    public String title;
    public String released;
    public String rated;
    public String genre;
    public String runtime;

    public String plot;
    public String rtScore;
    public String imdbScore;
    public String metaScore;
    public String posterURL;
    public String imdbId;

    public String website;

    public Movie() { }

    public Movie(String title, String year) {
        this.title = title;
        this.released = year;
    }
}
