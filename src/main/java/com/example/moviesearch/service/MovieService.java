package com.example.moviesearch.service;

import com.example.moviesearch.model.MovieDetails;
import com.example.moviesearch.model.MovieSummary;
import com.example.moviesearch.model.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final OmdbClient omdbClient;

    public MovieService(OmdbClient omdbClient) {
        this.omdbClient = omdbClient;
    }

    public List<MovieSummary> searchMovies(String query) {
        return omdbClient.searchMovies(query);
    }

    public MovieDetails getMovieDetails(String imdbId, String plotLength) {
        return omdbClient.getMovieDetails(imdbId, plotLength);
    }

    public List<Rating> getRatings(String imdbId) {
        MovieDetails details = getMovieDetails(imdbId, "short");
        return details.getRatings();
    }

    public String getPlot(String imdbId, String plotLength) {
        MovieDetails details = getMovieDetails(imdbId, plotLength);
        return details.getPlot();
    }
}

