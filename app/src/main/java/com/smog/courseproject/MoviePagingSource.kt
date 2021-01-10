package com.smog.courseproject

import android.content.Context
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.bumptech.glide.load.HttpException
import com.smog.courseproject.data.Movie
import com.smog.courseproject.data.loadMovies
import java.io.IOException

fun getDefaultPageConfig(): PagingConfig {
    return PagingConfig(pageSize = 6, enablePlaceholders = false, initialLoadSize = 6)
}

class MoviePagingSource(
    val context: Context
): PagingSource<Int, Movie>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Movie> {
        try {
            val nextPageNumber = params.key ?: 0
            //params.loadSize - почему-то меняется со временем и получается неверный подсчет у nextPageNumber
            val response = loadMovies(context,nextPageNumber,8)
            return LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (response.isEmpty()) null else nextPageNumber+1
            )
        }
        catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }
        catch (e: Exception) {
            // Other error
            return LoadResult.Error(e)
        }
    }
}