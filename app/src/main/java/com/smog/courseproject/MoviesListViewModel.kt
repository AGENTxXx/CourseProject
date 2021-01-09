package com.smog.courseproject

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.smog.courseproject.data.Movie
import kotlinx.coroutines.flow.Flow

class MoviesListViewModel : ViewModel() {

    fun fetchMovies(context: Context): Flow<PagingData<Movie>> {
        return letMoviesFlow(context)
            .cachedIn(viewModelScope)
    }

    private fun letMoviesFlow(context: Context): Flow<PagingData<Movie>> {
        val pageSize = 3

        //PagingConfig можно отдельно не выносить отдельно пока что, это наоборот только запутывает.
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                /**TODO здесь этот параметр необходим для того,
                 *  чтобы изначально подгрузить какое-то количество элементов.
                 *  Наверное из-за этого у тебя loadSize менялось.
                 *  (Я пока поставил такого же размера, как и pageSize,
                 *  потому что в другом случае оно отображает элементы криво и повторно,
                 *  а я пока не знаю как это обработать правильно)
                 **/
                initialLoadSize = pageSize
            )
        ) { MoviePagingSource(context) }.flow
    }
}