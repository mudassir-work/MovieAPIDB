package com.example.database_recycler.network


import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface TMDBApiService {
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("year") year: Int? = null
    ): TMDBResponse

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500" // Base URL for images

        fun create(): TMDBApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TMDBApiService::class.java)
        }

        fun getFullImagePath(imagePath: String?): String? {
            return if (imagePath != null) IMAGE_BASE_URL + imagePath else null
        }
    }
}

data class TMDBResponse(val results: List<TMDBMovies>)

