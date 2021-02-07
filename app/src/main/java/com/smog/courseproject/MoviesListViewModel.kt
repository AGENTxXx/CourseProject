package com.smog.courseproject

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.smog.courseproject.data.Movie
import com.smog.courseproject.data.MovieDb
import com.smog.courseproject.data.loadMovies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MoviesListViewModel : ViewModel() {

    fun fetchMovies(context: Context): Flow<PagingData<MovieDb>> {
        return letMoviesFlow(context)
            .cachedIn(viewModelScope)
    }

    private fun letMoviesFlow(context:Context, pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<MovieDb>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { MoviePagingSource(context) }
        ).flow
    }
}