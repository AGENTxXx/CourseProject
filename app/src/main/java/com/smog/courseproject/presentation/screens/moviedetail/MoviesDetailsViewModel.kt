package com.smog.courseproject.presentation.screens.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smog.courseproject.data.CastEntity
import com.smog.courseproject.data.MovieCastEntity
import com.smog.courseproject.data.MovieEntity
import com.smog.courseproject.data.local.dao.CastDao
import com.smog.courseproject.data.local.dao.MovieCastDao
import com.smog.courseproject.data.network.api.MoviesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class MoviesDetailsViewModel(
    movie: MovieEntity,
    private val castDao: CastDao,
    private val movieCastDao: MovieCastDao,
    private val moviesApi: MoviesApi
) : ViewModel() {

    val movieLiveData: LiveData<MovieEntity> = MutableLiveData(movie)

    val creditsLiveData: LiveData<List<CastEntity>>
        get() = _creditsLiveData
    private val _creditsLiveData = MutableLiveData<List<CastEntity>>()

    init {
        getCredits(movie.id!!)
    }

    private fun getCredits(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val credits = mutableListOf<CastEntity>()
            //TODO я бы этот метод как-нибудь более просто расписал, избавившись вообще от mutableList
            try {
                val result = moviesApi.getMovieCredits(movieId)
                credits.addAll(result.body()!!.cast ?: emptyList())
                castDao.insertAll(credits)
                movieCastDao.insert(MovieCastEntity(movieId, credits.map { it.castId!! }))
            } catch (e: IOException) {
                val movieCastRow = movieCastDao.getByMovieId(movieId.toLong())
                if (movieCastRow != null) {
                    credits.addAll(
                        castDao.getByIds(movieCastRow.castIds ?: emptyList())
                    )
                }
            }
            _creditsLiveData.postValue(credits)
        }
    }
}