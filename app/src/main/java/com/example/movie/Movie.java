package com.example.movie;

public class Movie {
    private String title;
    private String year;
    private String rating;
    private String runtime;


    // Constructor
    public Movie(String title, String year, String rating, String runtime) {
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.runtime = runtime;

    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getRating() {
        return rating;
    }

    public String getRuntime() {
        return runtime;

    }
}
