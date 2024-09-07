package com.example.Practice.Service;

import com.example.Practice.Controller.ReviewController;
import com.example.Practice.Film;
import com.example.Practice.Repository.ReviewRepository;
import com.example.Practice.Review;
import com.example.Practice.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public void remove(Integer id) {
        reviewRepository.deleteById(id);
    }

    public void update(Review review) {
        reviewRepository.save(review);
    }

    public Optional<Review> findReviewByUserAndFilm(User user, Integer film_id) {
        return reviewRepository.findByUserAndFilm(user, film_id);
    }
}
