package com.example.Practice.Service;

import com.example.Practice.Film;
import com.example.Practice.Repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FilmService {
    @Autowired
    private FilmRepository filmRepository;

    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    public Optional<Film> findFilmById(Integer filmId) {
        return filmRepository.findById(filmId);
    }
}
