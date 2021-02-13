package com.smog.courseproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smog.courseproject.data.MovieEntity

class MoviesDetailsViewModelFactory(private val movie:MovieEntity): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesDetailsViewModel::class.java -> MoviesDetailsViewModel(movie)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}