package com.smog.courseproject.presentation.screens.movielist

import android.util.Log
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.bumptech.glide.load.HttpException
import com.smog.courseproject.data.MovieEntity
import com.smog.courseproject.data.local.dao.MovieDao
import com.smog.courseproject.data.network.api.MoviesApi
import java.io.IOException

fun getDefaultPageConfig(): PagingConfig {
    return PagingConfig(pageSize = 6, enablePlaceholders = false, initialLoadSize = 6)
}

class MoviePagingSource(
    //TODO убрал синглтон и прокинул его в конструктор
    private val movieDao: MovieDao,
    private val moviesApi: MoviesApi
) : PagingSource<Int, MovieEntity>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, MovieEntity> {
        try {
            val nextPageNumber = params.key ?: 1

            var movies: List<MovieEntity>?
            try {
                val response = moviesApi.getMovies(nextPageNumber)
                movies = response.body()!!.results!!
                movieDao.insertAll(movies)
            } catch (e: IOException) {
                movies = movieDao.getLimitItems(12, (nextPageNumber - 1) * 12)
            }

            return LoadResult.Page(
                data = movies!!,
                prevKey = null,
                nextKey = if (movies.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: IOException) {
            // IOException for network failures.
            Log.e("ERROR", e.message!!)
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            Log.e("ERROR", e.message!!)
            return LoadResult.Error(e)
        } catch (e: Exception) {
            // Other error
            Log.e("ERROR", e.message!!)
            return LoadResult.Error(e)
        }
    }
}