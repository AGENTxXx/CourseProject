package com.smog.courseproject.presentation.screens.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smog.courseproject.data.MovieEntity
import com.smog.courseproject.data.local.dao.CastDao
import com.smog.courseproject.data.local.dao.MovieCastDao
import com.smog.courseproject.data.network.api.MoviesApi

class MoviesDetailsViewModelFactory(
    private val movie: MovieEntity,
    private val castDao: CastDao,
    private val movieCastDao: MovieCastDao,
    private val moviesApi: MoviesApi
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesDetailsViewModel::class.java -> MoviesDetailsViewModel(
            movie = movie,
            castDao = castDao,
            movieCastDao = movieCastDao,
            moviesApi = moviesApi
        )
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}