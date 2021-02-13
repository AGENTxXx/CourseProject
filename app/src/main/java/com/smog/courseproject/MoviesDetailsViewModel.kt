package com.smog.courseproject

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smog.courseproject.data.CastEntity
import com.smog.courseproject.data.MovieCastEntity
import com.smog.courseproject.data.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class MoviesDetailsViewModel(
    movie: MovieEntity
) : ViewModel() {
    val movieLiveData = MutableLiveData(movie)
    var creditsLiveData = MutableLiveData<List<CastEntity>>()

    fun getCredits(movieId:Int):MutableLiveData<List<CastEntity>> {
        val castDao = MainActivity.dbApp.castDao()
        val movieCastDao = MainActivity.dbApp.movieCastDao()
        GlobalScope.launch() {
            withContext(Dispatchers.IO) {
                var credits: List<CastEntity>? = listOf()
                try {
                    val result = RetrofitModule.moviesApi.getMovieCredits(movieId)
                    credits = result.body()!!.cast
                    castDao?.insertAll(credits)
                    movieCastDao?.insert(MovieCastEntity(movieId, credits?.map { it.castId!! }))
                } catch (e: IOException) {
                    val movieCastRow = movieCastDao?.getByMovieId(movieId.toLong())
                    if (movieCastRow != null) {
                        credits = castDao?.getByIds(movieCastRow.castIds)
                    }
                }
                creditsLiveData.postValue(credits)
            }
        }

        return creditsLiveData
    }
}