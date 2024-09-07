package com.example.Practice.Controller;

import com.example.Practice.*;
import com.example.Practice.Repository.UserRepository;
import com.example.Practice.Service.FilmService;
import com.example.Practice.Service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/practice")
public class FilmController {
    @Autowired
    private FilmService filmService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/films")
    public String allFilms(HttpSession session, Model model) {
        List<Film> films = filmService.getAllFilms();
        List<List<Film>> filmspartitioned = partition(films, 3);
        String username = (String) session.getAttribute("username");
        User user = userRepository.findByUsername(username);
        String imageBase64 = null;

        if (user.getPfp() != null) {
            imageBase64 = Base64.getEncoder().encodeToString(user.getPfp());
        }

        String imageSrc = (imageBase64 != null) ? "data:image/jpeg;base64," + imageBase64 : "/images/default_pfp.png";

        model.addAttribute("imageSrc", imageSrc);
        model.addAttribute("user", user);
        model.addAttribute("films", filmspartitioned);

        return "films";
    }

    public List<List<Film>> partition(List<Film> films, int size) {
        List<List<Film>> partitionFilms = new ArrayList<>();
        for (int i = 0; i < films.size(); i += size) {
            partitionFilms.add(films.subList(i, Math.min(i + size, films.size())));
        }

        return partitionFilms;
    }

    @GetMapping("/films/{id}")
    public String filmPage(Model model, @PathVariable Integer id, HttpSession session) {
        Optional<Film> optionalFilm = filmService.findFilmById(id);
        String username = (String) session.getAttribute("username");
        User user = userRepository.findByUsername(username);
        String imageBase64 = null;

        if (user.getPfp() != null) {
            imageBase64 = Base64.getEncoder().encodeToString(user.getPfp());

            if (optionalFilm.isPresent()) {
                Film film = optionalFilm.get();
                List<Review> reviews = validReviews(film, reviewService.getAllReviews());
                film.parseAllScores();
                if (film.getScore() == null) {
                    film.setScore(0.f);
                }
                System.out.println("Film: " + film);
                model.addAttribute("film", film);
                model.addAttribute("reviews", reviews);
            }
        }
        String imageSrc = (imageBase64 != null) ? "data:image/jpeg;base64," + imageBase64 : "/images/default_pfp.png";
        model.addAttribute("user", user);
        model.addAttribute("imageSrc", imageSrc);


        return "filmPage";
    }

    @PostMapping("/checkBalance")
    public ResponseEntity<String> checkBalance(@RequestBody ReviewUtility reviewUtility,
                                               HttpSession session) {
        String username = (String) session.getAttribute("username");

        if (username.equals(reviewUtility.getUsername())) {
            User user = userRepository.findByUsername(username);
            Optional<Film> optionalFilm = filmService.findFilmById(reviewUtility.getFilmId());
            if (optionalFilm.isPresent()) {
                Film film = optionalFilm.get();
                if (user.getBalance() >= film.getPrice()) {
                    user.setBalance(user.getBalance() - film.getPrice());
                    user.addFilm(film);
                    userRepository.save(user);
                    return ResponseEntity.ok("payment successful");
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not enough balance");
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body("film was not found");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username not found");
    }

    public List<Review> validReviews(Film filmId, List<Review> reviewsForFilm) {
        List<Review> correctReviewsForFilm = new ArrayList<>();

        for (Review review : reviewsForFilm) {
            if (review.getFilm_id().equals(filmId.getId())) {
                correctReviewsForFilm.add(review);
            }
        }
        return correctReviewsForFilm;
    }

    @GetMapping("/bankForm/{id}/{price}")
    public String renderBankForm(@PathVariable Integer id,
                                 @PathVariable Integer price,
                                 Model model) {
        model.addAttribute("price", price);
        model.addAttribute("filmId", id);

        return "bankForm";
    }

    @PostMapping("/checkIfBought")
    public ResponseEntity<String> checkBought(@RequestBody FilmDetails filmDetails,
                                              HttpSession session) {
        String username = (String) session.getAttribute("username");
        User user = userRepository.findByUsername(username);
        Optional<Film> optionalFilm = filmService.findFilmById(Integer.parseInt(filmDetails.getFilmId()));

        if (optionalFilm.isPresent()) {
            Film film = optionalFilm.get();

            if (user.getBoughtFilms().contains(film)) {
                return ResponseEntity.ok("success");
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("film was not bought");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no such film");
    }
}