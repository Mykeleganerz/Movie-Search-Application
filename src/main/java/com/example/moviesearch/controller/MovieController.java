package com.example.moviesearch.controller;

import com.example.moviesearch.model.MovieDetails;
import com.example.moviesearch.model.MovieSummary;
import com.example.moviesearch.model.Rating;
import com.example.moviesearch.service.MovieService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@Validated
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/search")
    public List<MovieSummary> searchMovies(@RequestParam("query") @NotBlank String query) {
        return movieService.searchMovies(query);
    }

    @GetMapping("/{imdbId}")
    public MovieDetails getMovieDetails(
            @PathVariable("imdbId") String imdbId,
            @RequestParam(name = "plot", defaultValue = "short") String plotLength
    ) {
        return movieService.getMovieDetails(imdbId, plotLength);
    }

    @GetMapping("/{imdbId}/ratings")
    public List<Rating> getRatings(@PathVariable("imdbId") String imdbId) {
        return movieService.getRatings(imdbId);
    }

    @GetMapping("/{imdbId}/plot")
    public String getPlot(
            @PathVariable("imdbId") String imdbId,
            @RequestParam(name = "length", defaultValue = "short") String plotLength
    ) {
        return movieService.getPlot(imdbId, plotLength);
    }
}

