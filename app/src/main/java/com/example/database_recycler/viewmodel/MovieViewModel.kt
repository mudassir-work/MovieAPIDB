package com.example.database_recycler.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.database_recycler.database.Movie
import com.example.database_recycler.database.MovieDao
import com.example.database_recycler.database.MovieDatabase
import com.example.database_recycler.network.TMDBApiService
import com.example.database_recycler.network.TMDBResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val movieDao: MovieDao = MovieDatabase.getDatabase(application).movieDao()
    val allMovies: LiveData<List<Movie>> = movieDao.getAllMovies()
    private val tmdbApi = TMDBApiService.create()

    fun addMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = tmdbApi.searchMovies(
                    apiKey = "ef42bb40dd4365f391cc96165bdcabfa",
                    query = movie.name,
                    year = movie.releaseYear
                )
                val tmdbMovie = response.results.firstOrNull()
                if (tmdbMovie != null) {
                    val movieWithDetails = movie.copy(
                        story = tmdbMovie.overview,
                        posterUrl = TMDBApiService.getFullImagePath(tmdbMovie.poster_path),
                        rating = tmdbMovie.vote_average
                    )
                    movieDao.insertMovie(movieWithDetails)
                } else {
                    movieDao.insertMovie(movie) // Insert the movie without TMDB details
                }
            } catch (e: Exception) {
                movieDao.insertMovie(movie) // Insert the movie without TMDB details in case of an error
            }
        }
    }


    fun deleteMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            movieDao.deleteMovie(movie)
        }
    }
}









