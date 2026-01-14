# Movie Search Application API

This is a simple Spring Boot API that lets you search for movies using the OMDb API and view ratings and plot information.

The API is meant to be called from tools like a browser, Postman, or curl. There is no separate web UI; you interact with it through HTTP endpoints.

---

## 1. Prerequisites

- Java 17 installed
- Maven installed
- An OMDb API key (you can get one from https://www.omdbapi.com/apikey.aspx)

---

## 2. Configuration

Open [src/main/resources/application.properties](file:///c:/Users/egana/OneDrive/Desktop/Movie-Search-Application/src/main/resources/application.properties) and set your OMDb API key:

```properties
omdb.api.base-url=https://www.omdbapi.com/
omdb.api.key=YOUR_REAL_OMDB_API_KEY
```

Replace `YOUR_REAL_OMDB_API_KEY` with your actual key.

By default, the API runs on port `8080`:

```properties
server.port=8080
```

So the base URL will be:

- `http://localhost:8080`

---

## 3. Running the Application

From the project root:

```bash
mvn spring-boot:run
```

Once it starts, you can call the endpoints from a browser or a REST client.

---

## 4. Menu Endpoint (Overview of Options)

Endpoint:

- `GET http://localhost:8080/api/menu`

This returns a JSON "menu" with options for what you can do and which endpoint to call. It is a good starting point for users who want to see available actions.

Example response (simplified):

```json
{
  "title": "Movie Search Application Menu",
  "description": "Choose an option and call the corresponding endpoint with your input.",
  "options": [
    { "id": 1, "name": "Search movies by title", "endpoint": "/api/movies/search?query={title}" },
    { "id": 2, "name": "Get movie details including ratings and plot", "endpoint": "/api/movies/{imdbId}?plot=full" }
  ]
}
```

---

## 5. Search Movies by Title

Endpoint:

- `GET /api/movies/search?query={title}`

Full URL example:

- `http://localhost:8080/api/movies/search?query=inception`

What it does:

- Searches OMDb for movies whose title matches the `query` value.
- Returns a list of movie summaries with IMDb ID, title, year, type, and poster.

Example using curl:

```bash
curl "http://localhost:8080/api/movies/search?query=inception"
```

---

## 6. Get Full Movie Details (including ratings and plot)

Endpoint:

- `GET /api/movies/{imdbId}?plot={plotLength}`

Parameters:

- `imdbId` (path): IMDb ID of the movie, for example `tt1375666`.
- `plot` (query, optional): `short` (default) or `full`.

Full URL example:

- `http://localhost:8080/api/movies/tt1375666?plot=full`

What it does:

- Calls OMDb to get detailed information for the given movie.
- Returns title, year, rating, runtime, genre, director, actors, plot, IMDb rating, and a list of ratings.

Example using curl:

```bash
curl "http://localhost:8080/api/movies/tt1375666?plot=full"
```

---

## 7. Get Ratings Only

Endpoint:

- `GET /api/movies/{imdbId}/ratings`

Full URL example:

- `http://localhost:8080/api/movies/tt1375666/ratings`

What it does:

- Returns only the ratings for the given IMDb ID (for example, IMDb, Rotten Tomatoes, Metacritic).

Example using curl:

```bash
curl "http://localhost:8080/api/movies/tt1375666/ratings"
```

---

## 8. Get Plot Only (Short or Full)

Endpoint:

- `GET /api/movies/{imdbId}/plot?length={length}`

Parameters:

- `imdbId` (path): IMDb ID of the movie.
- `length` (query, optional): `short` or `full` (defaults to `short`).

Full URL examples:

- Short plot: `http://localhost:8080/api/movies/tt1375666/plot?length=short`
- Full plot: `http://localhost:8080/api/movies/tt1375666/plot?length=full`

What it does:

- Returns just the plot text for the chosen movie and length.

Example using curl:

```bash
curl "http://localhost:8080/api/movies/tt1375666/plot?length=full"
```

---

## 9. Typical User Flow

1. Start the application with `mvn spring-boot:run`.
2. Open `http://localhost:8080/api/menu` to see available options and endpoints.
3. Use the search endpoint to find a movie by title.
4. Copy an `imdbId` from the search results.
5. Use that `imdbId` to:
   - Get full details (including ratings and plot), or
   - Get ratings only, or
   - Get plot only (short or full).

With these endpoints, a user can explore movies, read plots, and check ratings directly via the API.
