package com.example.Practice;

import jakarta.persistence.*;

@Entity
@Table(name="user_practice_1_reviews")
public class UserReviews {
    @Id
    @SequenceGenerator(
            name = "user_practice_1_reviews_id_seq",
            sequenceName = "user_practice_1_reviews_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_practice_1_reviews_id_seq"
    )
    private Integer id;
    private Integer user_id;
    private Integer reviews_id;

    public UserReviews() {

    }

    public UserReviews(Integer user_id, Integer reviews_id) {
        this.user_id = user_id;
        this.reviews_id = reviews_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getReviews_id() {
        return reviews_id;
    }

    public void setReviews_id(Integer reviews_id) {
        this.reviews_id = reviews_id;
    }
}
