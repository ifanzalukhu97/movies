package com.example.movies.domain

import com.squareup.moshi.Json

/**
 * Movie API result object
 */
data class Movie(
    val results: List<Result>
)

data class Result(
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