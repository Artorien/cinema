package com.example.Practice.Repository;

import com.example.Practice.Review;
import com.example.Practice.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query("SELECT r FROM Review r WHERE r.user = :user AND r.film_id = :film_id")
    Optional<Review> findByUserAndFilm(@Param("user") User user, @Param("film_id") Integer film_id);
}
