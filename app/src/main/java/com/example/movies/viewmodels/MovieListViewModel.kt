package com.example.movies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movies.domain.Movie

class MovieListViewModel : ViewModel() {

    // Internal Movies List to store movies result from API
    private val _movies = MutableLiveData<List<Movie>>()

    // External Movies List to store movies result from API
    val movies: LiveData<List<Movie>>
        get() = _movies

    init {

    }

}