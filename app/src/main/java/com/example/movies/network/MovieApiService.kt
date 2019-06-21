package com.example.movies.network

import com.example.movies.domain.Movies
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org/3/"

/**
 * Build the Moshi object as a Retrofit converter
 * for parsing the JSON format
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface MovieApiService {

    /**
     * Get Movies List by Category from API
     *
     * @param movieCategory is movies path category (eq. popular, now_playing, top_rated)
     *
     * Coroutine Call Adapter allows us to
     * @return a Deferred<Movies>, a Job with a result
     */
    @GET("movie/{category}")
    fun getMoviesAsync(
        @Path("category") movieCategory: String,
        @Query("api_key") apiKey: String
    ): Deferred<Movies>

    @GET("search/movie/")
    fun getSearchMoviesAsync(
        @Query("query") movieTitle: String,
        @Query("api_key") apiKey: String
    ): Deferred<Movies>
}

object MovieApi {
    val retrofitService: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }
}