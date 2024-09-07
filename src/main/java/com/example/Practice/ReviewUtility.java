package com.example.Practice;

public class ReviewUtility {
    private String username;
    private Integer filmId;

    public ReviewUtility(String username, Integer filmId) {
        this.username = username;
        this.filmId = filmId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }
}