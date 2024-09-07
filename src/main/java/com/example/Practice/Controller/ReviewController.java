package com.example.Practice.Controller;

import com.example.Practice.*;
import com.example.Practice.Repository.UserRepository;
import com.example.Practice.Service.FilmService;
import com.example.Practice.Service.ReviewService;
import com.example.Practice.Service.UserReviewsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/practice")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FilmService filmService;

    @Autowired
    private UserReviewsService userReviewsService;

    @PostMapping("/review")
    public ResponseEntity<String> saveReview(@RequestBody ReviewContent reviewContent,
                             HttpSession session) {
        String username = (String) session.getAttribute("username");

        User user = userRepository.findByUsername(username);
        Review review = new Review();
        Optional<Film> optionalFilm = filmService.findFilmById(reviewContent.getFilmId());
        List<Review> reviews = user.getReviews();

        review.setUser(user);
        review.setFilm_id(reviewContent.getFilmId());
        review.setUsername(user.getUsername());
        review.setContent(reviewContent.getReviewContent());
        review.setGrade(reviewContent.getStars());

        Optional<Review> existingReview = reviewService.findReviewByUserAndFilm(user, optionalFilm.get().getId());

        if (existingReview.isPresent()) {
            Review existing = existingReview.get();
            existing.setContent(reviewContent.getReviewContent());
            existing.setGrade(reviewContent.getStars());
            reviewService.update(existing);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Review updated");
        } else {
            reviews.add(review);
            session.setAttribute("reviews", reviews);
            reviewService.saveReview(review);
            optionalFilm.ifPresent(film -> {
                film.addReview(review);
                film.parseAllScores();
            });

            return ResponseEntity.ok("Review added");
        }
    }

    @PostMapping("/checking")
    public ResponseEntity<String> checking(@RequestBody UserCheck userCheck, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username.equals(userCheck.getOwner())) {
            return ResponseEntity.ok("match found");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username does not match");
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody ReviewUtility reviewUtility,
                                         HttpSession session) {
        String username = (String) session.getAttribute("username");
        User user = userRepository.findByUsername(username);

        if (reviewUtility.getUsername().equals(user.getUsername())) {
            List<Review> reviews = user.getReviews();
            Iterator<Review> iterator = reviews.iterator();
            boolean reviewRemoved = false;
            System.out.println(reviews);

            for (Review review : reviews) {
                System.out.println(review.getFilm_id());
            }

            while (iterator.hasNext()) {
                Review review = iterator.next();
                if (review.getFilm_id().equals(reviewUtility.getFilmId())) {
                    System.out.println("Review id: " + review.getId());
                    userReviewsService.remove(review.getId());
                    reviewService.remove(review.getId());
                    iterator.remove();
                    reviewRemoved = true;
                }
            }

            user.setReviews(reviews);
            session.setAttribute("reviews", reviews);

            if (reviewRemoved) {
                return ResponseEntity.ok("review removed");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("review not found");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("review not found");
    }

    @PostMapping("/update")
    public ResponseEntity<ReviewInfo> update(@RequestBody ReviewUtility reviewUtility,
                             HttpSession session) {
        String username = (String) session.getAttribute("username");
        User user = userRepository.findByUsername(username);

        if (reviewUtility.getUsername().equals(user.getUsername())) {
            List<Review> reviews = user.getReviews();

            for (Review review : reviews) {
                if (review.getFilm_id().equals(reviewUtility.getFilmId())) {
                    ReviewInfo reviewInfo = new ReviewInfo(review.getGrade(), review.getContent());
                    return ResponseEntity.ok(reviewInfo);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping("/formState")
    public ResponseEntity<String> checkState(@RequestBody ReviewUtility reviewUtility,
                                             HttpSession session) {
        String username = (String) session.getAttribute("username");
        User user = userRepository.findByUsername(username);
        Optional<Review> review = reviewService.findReviewByUserAndFilm(user, reviewUtility.getFilmId());

        if (review.isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND).body("Review was found");
        } else {
            return ResponseEntity.ok("Nothing was found");
        }
    }
}
