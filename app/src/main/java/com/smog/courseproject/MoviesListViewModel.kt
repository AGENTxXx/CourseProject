package com.smog.courseproject

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.smog.courseproject.data.MovieEntity
import kotlinx.coroutines.flow.Flow

class MoviesListViewModel : ViewModel() {

    fun fetchMovies(context: Context): Flow<PagingData<MovieEntity>> {
        return letMoviesFlow(context)
            .cachedIn(viewModelScope)
    }

    private fun letMoviesFlow(context:Context, pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<MovieEntity>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { MoviePagingSource(context) }
        ).flow
    }
}