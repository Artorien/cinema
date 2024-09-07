package com.example.Practice;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_practice_1")
public class User {
    @Id
    @SequenceGenerator(
            name = "user_practice_1_id_seq",
            sequenceName = "user_practice_1_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_practice_1_id_seq"
    )
    private Integer id;
    private String username;
    private String password;
    private byte[] pfp;
    @OneToMany
    private List<Review> reviews;
    @OneToMany
    private List<Film> boughtFilms;
    @Transient
    private String defaultPfp;
    private Integer balance;
    @OneToMany
    private List<User> friends;

    public User() {

    }

    public User(String username, String password, String defaultPfp) {
        this.username = username;
        this.password = password;
        this.pfp = new byte[0];
        this.defaultPfp = defaultPfp;
        this.balance = 0;
        this.boughtFilms = new ArrayList<>();
        this.friends = new ArrayList<>();

    }

    public byte[] getPfp() {
        return pfp;
    }

    public String getDefaultPfp() {
        return defaultPfp;
    }

    public void setDefaultPfp(String defaultPfp) {
        this.defaultPfp = defaultPfp;
    }

    public void setPfp(byte[] pfp) {
        this.pfp = pfp;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public List<Film> getBoughtFilms() {
        return boughtFilms;
    }

    public void setBoughtFilms(List<Film> boughtFilms) {
        this.boughtFilms = boughtFilms;
    }

    public void addFilm(Film film) {
        this.boughtFilms.add(film);
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }
}