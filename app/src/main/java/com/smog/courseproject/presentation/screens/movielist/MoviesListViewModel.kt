package com.smog.courseproject.presentation.screens.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.smog.courseproject.data.MovieEntity
import com.smog.courseproject.data.local.dao.MovieDao
import com.smog.courseproject.data.network.api.MoviesApi
import kotlinx.coroutines.flow.Flow

class MoviesListViewModel(
    //TODO тоже прокидываю в конструктор вместо явного использования
    private val movieDao: MovieDao,
    private val moviesApi: MoviesApi
) : ViewModel() {

    fun fetchMovies(): Flow<PagingData<MovieEntity>> {
        return letMoviesFlow()
            .cachedIn(viewModelScope)
    }

    private fun letMoviesFlow(
        pagingConfig: PagingConfig = getDefaultPageConfig()
    ): Flow<PagingData<MovieEntity>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { MoviePagingSource(movieDao, moviesApi) }
        ).flow
    }
}