package com.example.moviesearch.service;

import com.example.moviesearch.config.OmdbProperties;
import com.example.moviesearch.model.MovieDetails;
import com.example.moviesearch.model.MovieSummary;
import com.example.moviesearch.model.Rating;
import com.example.moviesearch.omdb.OmdbMovieDetailsResponse;
import com.example.moviesearch.omdb.OmdbRating;
import com.example.moviesearch.omdb.OmdbSearchMovie;
import com.example.moviesearch.omdb.OmdbSearchResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OmdbClient {

    private final RestTemplate restTemplate;
    private final OmdbProperties properties;

    public OmdbClient(OmdbProperties properties) {
        this.properties = properties;
        this.restTemplate = new RestTemplate();
    }

    public List<MovieSummary> searchMovies(String query) {
        if (query == null || query.isBlank()) {
            return Collections.emptyList();
        }

        MultiValueMap<String, String> params = baseParams();
        params.add("s", query);
        params.add("type", "movie");

        OmdbSearchResponse response = getForObject(params, OmdbSearchResponse.class);

        if (response == null || !"True".equalsIgnoreCase(response.getResponse())) {
            return Collections.emptyList();
        }

        if (response.getSearch() == null) {
            return Collections.emptyList();
        }

        return response.getSearch().stream()
                .filter(Objects::nonNull)
                .map(this::toMovieSummary)
                .collect(Collectors.toList());
    }

    public MovieDetails getMovieDetails(String imdbId, String plotLength) {
        if (imdbId == null || imdbId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "imdbId is required");
        }

        MultiValueMap<String, String> params = baseParams();
        params.add("i", imdbId);
        params.add("plot", plotLength == null || plotLength.isBlank() ? "short" : plotLength);

        OmdbMovieDetailsResponse response = getForObject(params, OmdbMovieDetailsResponse.class);

        if (response == null || !"True".equalsIgnoreCase(response.getResponse())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }

        return toMovieDetails(response);
    }

    private MultiValueMap<String, String> baseParams() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("apikey", properties.getKey());
        return params;
    }

    private <T> T getForObject(MultiValueMap<String, String> params, Class<T> type) {
        if (properties.getKey() == null || properties.getKey().isBlank()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "OMDb API key is not configured");
        }

        String url = properties.getBaseUrl();
        if (url == null || url.isBlank()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "OMDb base URL is not configured");
        }

        StringBuilder builder = new StringBuilder(url);
        if (!url.contains("?")) {
            builder.append("?");
        }

        boolean first = true;
        for (String key : params.keySet()) {
            List<String> values = params.get(key);
            if (values == null) {
                continue;
            }
            for (String value : values) {
                if (!first) {
                    builder.append("&");
                }
                builder.append(key).append("=").append(value);
                first = false;
            }
        }

        try {
            return restTemplate.getForObject(builder.toString(), type);
        } catch (RestClientException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error calling OMDb API");
        }
    }

    private MovieSummary toMovieSummary(OmdbSearchMovie source) {
        return new MovieSummary(
                source.getImdbId(),
                source.getTitle(),
                source.getYear(),
                source.getType(),
                source.getPoster()
        );
    }

    private MovieDetails toMovieDetails(OmdbMovieDetailsResponse source) {
        List<Rating> ratings = source.getRatings() == null
                ? Collections.emptyList()
                : source.getRatings().stream()
                .filter(Objects::nonNull)
                .map(this::toRating)
                .collect(Collectors.toList());

        return new MovieDetails(
                source.getImdbId(),
                source.getTitle(),
                source.getYear(),
                source.getRated(),
                source.getRuntime(),
                source.getGenre(),
                source.getDirector(),
                source.getActors(),
                source.getPlot(),
                source.getImdbRating(),
                ratings
        );
    }

    private Rating toRating(OmdbRating source) {
        return new Rating(source.getSource(), source.getValue());
    }
}

