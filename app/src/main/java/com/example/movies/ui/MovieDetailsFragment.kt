package com.example.movies.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.movies.databinding.FragmentMovieDetailsBinding
import com.example.movies.viewmodels.MovieDetailsViewModel

class MovieDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMovieDetailsBinding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        // Get Selected Movie From Action Arguments
        val selectedMovie = MovieDetailsFragmentArgs.fromBundle(arguments!!).selectedMovie

        // Parsing selectedMovie as arguments to View Model
        val viewModel = ViewModelProviders.of(this, MovieDetailsViewModel.Factory(selectedMovie))
            .get(MovieDetailsViewModel::class.java)

        binding.movie = viewModel.selectedMovie.value

        return binding.root
    }


}
