package com.example.database_recycler.database


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val releaseYear: Int,
    val cast: String,
    val story: String? = null,
    val posterUrl: String? = null, // Poster image URL
    val rating: Double? = null     // Rating
)




