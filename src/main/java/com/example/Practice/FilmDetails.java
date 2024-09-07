package com.example.Practice;

public class FilmDetails {
    private String price;
    private String filmId;

    public FilmDetails() {

    }

    public FilmDetails(String price, String filmId) {
        this.price = price;
        this.filmId = filmId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }
}