package com.smog.courseproject

import android.content.Context
import android.util.Log
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.bumptech.glide.load.HttpException
import com.smog.courseproject.MainActivity.Companion.dbApp
import com.smog.courseproject.data.MovieEntity
import java.io.IOException

fun getDefaultPageConfig(): PagingConfig {
    return PagingConfig(pageSize = 6, enablePlaceholders = false, initialLoadSize = 6)
}

class MoviePagingSource(
    val context: Context
) : PagingSource<Int, MovieEntity>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, MovieEntity> {
        try {
            val nextPageNumber = params.key ?: 1

            val movieDao = dbApp.movieDao()
            var movies: List<MovieEntity>?
            try {
                val response = RetrofitModule.moviesApi.getMovies(nextPageNumber)
                movies = response.body()!!.results!!
                movieDao?.insertAll(movies)
            } catch (e: IOException) {
                movies = movieDao?.getLimitItems(12, (nextPageNumber - 1) * 12)
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