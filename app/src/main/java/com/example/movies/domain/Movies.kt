package com.example.movies.domain

import com.squareup.moshi.Json

/**
 * Movies API result object
 */
data class Movies(
    val results: List<Movie>
)

data class Movie(
    val id: Int,
    @Json(name = "vote_average")
    val voteAverage: Double,
    val title: String,
    @Json(name = "poster_path")
    val poster: String,
    val overview: String,
    @Json(name = "release_date")
    val release: String
)