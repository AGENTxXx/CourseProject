package com.smog.courseproject.presentation.screens.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smog.courseproject.data.local.dao.MovieDao
import com.smog.courseproject.data.network.api.MoviesApi

class MoviesListViewModelFactory(
    private val movieDao: MovieDao,
    private val moviesApi: MoviesApi
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesListViewModel::class.java -> MoviesListViewModel(movieDao, moviesApi)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}