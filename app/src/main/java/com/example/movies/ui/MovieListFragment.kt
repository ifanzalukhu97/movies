package com.example.movies.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.movies.R
import com.example.movies.databinding.FragmentMovieListBinding

class MovieListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentMovieListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_movie_list, container, false
        )

        return binding.root
    }


}
