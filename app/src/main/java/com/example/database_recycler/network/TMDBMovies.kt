package com.example.database_recycler.network

data class TMDBMovies(
    val title: String,        // Movie title
    val overview: String,     // Story/overview
    val release_date: String, // Release date
    val poster_path: String?, // Poster image path
    val vote_average: Double  // Rating
)
