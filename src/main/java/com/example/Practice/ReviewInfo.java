package com.example.Practice;

public class ReviewInfo {
    private Integer stars;
    private String reviewData;

    public ReviewInfo(Integer stars, String reviewData) {
        this.stars = stars;
        this.reviewData = reviewData;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getReviewData() {
        return reviewData;
    }

    public void setReviewData(String reviewData) {
        this.reviewData = reviewData;
    }
}