package com.example.Practice;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @SequenceGenerator(
            name = "review_id_seq",
            sequenceName = "review_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "review_id_seq"
    )
    private Integer id;
    private String content;
    private Integer grade;
    @ManyToOne
    private User user;
    @Transient
    private String username;
    private Integer film_id;

    public Review() {

    }

    public Review(String content, Integer grade, User user, String username, Integer film_id) {
        this.content = content;
        this.grade = grade;
        this.user = user;
        this.username = username;
        this.film_id = film_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getFilm_id() {
        return film_id;
    }

    public void setFilm_id(Integer film_id) {
        this.film_id = film_id;
    }
}