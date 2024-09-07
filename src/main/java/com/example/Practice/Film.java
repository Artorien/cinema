package com.example.Practice;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "films")
public class Film {
    @Id
    @SequenceGenerator(
            name = "films_id_seq",
            sequenceName = "films_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "films_id_seq"
    )
    private Integer id;
    private String poster;
    private String title;
    private String description;
    @OneToMany
    private List<Review> reviews;
    private Float score;
    private Integer price;
    private String trailer;

    public Film() {

    }

    public Film(String poster, String title, String description, List<Review> reviews, Integer price, String trailer) {
        this.poster = poster;
        this.title = title;
        this.description = description;
        this.score = 0.f;
        this.reviews = new ArrayList<>();
        this.price = price;
        this.trailer = trailer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void parseAllScores() {
        float score = 0;

        for (Review review : this.reviews) {
            score += review.getGrade();
        }

        this.score = score / this.reviews.size();
    }
}