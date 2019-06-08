package com.example.movies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movies.domain.Movie

class MovieDetailsViewModel(movie: Movie) : ViewModel() {

    private val _selectedMovie = MutableLiveData<Movie>()

    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie

    init {
        _selectedMovie.value = movie
    }

    /**
     * Factory for constructing MovieDetailsViewModel with parameter
     */
    class Factory(private val movie: Movie) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MovieDetailsViewModel(movie) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }

    }
}