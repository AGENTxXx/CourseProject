package com.smog.courseproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smog.courseproject.data.Movie

class MoviesDetailsViewModel(
    movie: Movie
) : ViewModel() {
    val movieLiveData = MutableLiveData(movie)
}