package com.smog.courseproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smog.courseproject.data.Movie
import com.smog.courseproject.data.MovieDb

class MoviesDetailsViewModel(
    movie: MovieDb
) : ViewModel() {
    val movieLiveData = MutableLiveData(movie)
}