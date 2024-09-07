package com.example.Practice;

public class ReviewContent {
    private Integer stars;
    private String reviewContent;
    private Integer filmId;

    public ReviewContent(Integer stars, String reviewContent, Integer filmId) {
        this.stars = stars;
        this.reviewContent = reviewContent;
        this.filmId = filmId;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }
}