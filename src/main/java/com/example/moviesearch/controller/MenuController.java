package com.example.moviesearch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @GetMapping
    public Map<String, Object> getMenu() {
        return Map.of(
                "title", "Movie Search Application Menu",
                "description", "Choose an option and call the corresponding endpoint with your input.",
                "options", List.of(
                        Map.of(
                                "id", 1,
                                "name", "Search movies by title",
                                "endpoint", "/api/movies/search?query={title}"
                        ),
                        Map.of(
                                "id", 2,
                                "name", "Get movie details including ratings and plot",
                                "endpoint", "/api/movies/{imdbId}?plot=full"
                        ),
                        Map.of(
                                "id", 3,
                                "name", "Get ratings only",
                                "endpoint", "/api/movies/{imdbId}/ratings"
                        ),
                        Map.of(
                                "id", 4,
                                "name", "Get plot summary",
                                "endpoint", "/api/movies/{imdbId}/plot?length=short"
                        ),
                        Map.of(
                                "id", 5,
                                "name", "Get full plot",
                                "endpoint", "/api/movies/{imdbId}/plot?length=full"
                        )
                )
        );
    }
}

