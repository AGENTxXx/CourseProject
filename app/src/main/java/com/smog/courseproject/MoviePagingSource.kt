package com.smog.courseproject

import android.content.Context
import android.util.Log
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.bumptech.glide.load.HttpException
import com.smog.courseproject.data.Movie
import com.smog.courseproject.data.MovieDb
import com.smog.courseproject.data.loadMovies
import java.io.IOException

fun getDefaultPageConfig(): PagingConfig {
    return PagingConfig(pageSize = 6, enablePlaceholders = false, initialLoadSize = 6)
}

class MoviePagingSource(
    val context: Context
): PagingSource<Int, MovieDb>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, MovieDb> {
        try {
            val nextPageNumber = params.key ?: 1
            //params.loadSize - почему-то меняется со временем и получается неверный подсчет у nextPageNumber
            //val response = loadMovies(context,nextPageNumber,8)
            val response = RetrofitModule.moviesApi.getMovies(nextPageNumber)
            val movies = response.body()!!.results!!
            return LoadResult.Page(
                data = movies,
                prevKey = null,
                nextKey = if (movies.isEmpty()) null else nextPageNumber+1
            )
        }
        catch (e: IOException) {
            // IOException for network failures.
            Log.e("ERROR",e.message!!)
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            Log.e("ERROR",e.message!!)
            return LoadResult.Error(e)
        }
        catch (e: Exception) {
            // Other error
            Log.e("ERROR",e.message!!)
            return LoadResult.Error(e)
        }
    }
}