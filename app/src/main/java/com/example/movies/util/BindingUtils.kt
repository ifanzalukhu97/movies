package com.example.movies.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

private const val BASE_URL_MOVIE_POSTER = "https://image.tmdb.org/t/p/w533_and_h300_bestv2/"

/**
 * Binding adapter used to display movie poster from
 * @param posterPath using Glide
 */
@BindingAdapter("moviePoster")
fun setMoviePoster(imageView: ImageView, posterPath: String) {
    Glide.with(imageView.context).load(BASE_URL_MOVIE_POSTER + posterPath).into(imageView)
}


@BindingAdapter("movieTitle")
fun setMovieTitle(textView: TextView, title: String) {
    textView.text = title
}

@BindingAdapter("movieRelease")
fun setMovieReleaseData(textView: TextView, releaseData: String) {
    textView.text = releaseData
}