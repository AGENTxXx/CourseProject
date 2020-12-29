package com.smog.courseproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smog.courseproject.data.Movie

class MoviesDetailsViewModel : ViewModel() {
    private var liveMovie = MutableLiveData<Movie>()
    var movie: LiveData<Movie> = liveMovie

    fun movieFormatter(movie: Movie) {
        liveMovie.value = movie
    }
}