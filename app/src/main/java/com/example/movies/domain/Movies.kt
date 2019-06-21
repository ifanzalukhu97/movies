package com.example.movies.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Movies API result object
 */
data class Movies(
    val results: List<Movie>
)

@Parcelize
data class Movie(
    val id: Int,
    @Json(name = "vote_average")
    val voteAverage: Double,
    val title: String,
    @Json(name = "poster_path")
    val poster: String?,
    val overview: String,
    @Json(name = "release_date")
    val release: String
) : Parcelable