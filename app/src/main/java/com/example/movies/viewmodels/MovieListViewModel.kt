package com.example.movies.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.movies.R
import com.example.movies.domain.Movie
import com.example.movies.network.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieListViewModel(application: Application) : AndroidViewModel(application) {

    private val apiKeyMovieDB = application.getString(R.string.API_KEY_MOVIEDB)

    // Job for Coroutine Scope, so coroutine able to cancel when needed
    private val viewModelJob = Job()

    // Coroutine run on Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    // Internal Movies List to store movies result from API
    private var _movies = MutableLiveData<List<Movie>>()

    // External Movies List to store movies result from API
    val movies: LiveData<List<Movie>>
        get() = _movies

    val moviesCategoriesFilter = arrayListOf("Popular", "Upcoming", "Top Rated")

    private var currentTextCategory: String = "Now Playing"

    init {
        getMoviesByCategory(convertStringToPath(currentTextCategory))
    }

    private fun getMoviesByCategory(path_category: String) {

        coroutineScope.launch {
            val getMoviesDeferred =
                MovieApi.retrofitService.getMoviesAsync(path_category, apiKeyMovieDB)
            try {
                val moviesList = getMoviesDeferred.await()

                _movies.value = moviesList.results
            } catch (e: Exception) {
                Log.e("MovieListViewModel", "getMovieList ${e.message}")
                _movies.value = listOf()
            }
        }
    }

    fun onCategoryFilterChanged(newTextCategory: String, isChecked: Boolean) {
        currentTextCategory = if (isChecked) {
            newTextCategory
        } else {
            "Now Playing"
        }

        getMoviesByCategory(convertStringToPath(currentTextCategory))
    }

    private val _navigateToSelectedMovie = MutableLiveData<Movie>()
    val navigateToSelectedMovie: LiveData<Movie>
        get() = _navigateToSelectedMovie

    fun showMovieDetails(movie: Movie) {
        _navigateToSelectedMovie.value = movie
    }

    fun showMovieDetailsComplete() {
        _navigateToSelectedMovie.value = null
    }

    /**
     * Convert string to path
     * eq. ["Popular" -> "popular"], ["Top Rated", "top_rated"]
     */
    private fun convertStringToPath(text: String): String =
        text.toLowerCase().replace(" ", "_")


    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Factory for constructing MovieListViewModel with parameter
     */
    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MovieListViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}