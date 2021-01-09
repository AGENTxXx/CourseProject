package com.smog.courseproject

import android.content.Context
import android.util.Log
import androidx.paging.PagingSource
import com.smog.courseproject.data.Movie
import com.smog.courseproject.data.loadMovies
import kotlinx.coroutines.delay


class MoviePagingSource(
    /**TODO так ка здесь у нас хранится ссылка на контекст,
     *  то лучше всего сюда прокидывать applicationContext, который синглтон, чтобы не допустить утечки памяти
     */
    private val context: Context
) : PagingSource<Int, Movie>() {

    private val TAG = "PAGING"

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Movie> {
        try {
            //Здесь достаем номер нашей страницы. Если его еще не было, то начинаем отсчет с 0
            val page = params.key ?: 0

            //Да, здесь мы будем высчитывать каждый раз этот список. И потом уже эмулировать пагинацию. Могу словами потом объяснить зачем.
            val response = loadMovies(context)

            //Здесь делаем небольшую задержку, словно мы откуда то загружаем с интернета эти страницы (это можно убрать, просто для примера добавил)
            delay(100)

            /** Так как мы сейчас эмулируем пагинацию, то здесь мы высчитываем  Здесь высчитываем
             * стартовую и конечную позиции для нашей страницы с элементами
             *
             */
            val startPosition = params.loadSize * page

            val calculatedEndPosition = startPosition + params.loadSize
            val endPosition = if (calculatedEndPosition >= response.size) {
                response.size
            } else {
                calculatedEndPosition
            }

            //Здесь мы уже из высчитанных позиций делаем sublist, словно мы его подгрузили с интернета
            val sublist = response.subList(startPosition, endPosition)


            //Логгирование
            Log.d(
                TAG,
                "Current page : $page. StartPos: $startPosition. EndPos: $endPosition. Current list : ${sublist.map { it.title }}"
            )

            return LoadResult.Page(
                data = sublist,
                //Если у нас страница с номером 0, то предыдущую нам не нужно подгружать, и мы возвращаем null
                prevKey = if (page == 0) {
                    null
                } else {
                    page - 1
                },
                //Если у нас последняя страница, то следующую нам не нужно подгружать, и мы возвращаем null
                nextKey = if (endPosition == response.size) {
                    null
                } else {
                    page + 1
                }
            )
            
        } catch (e: Exception) {
            Log.e(TAG, e.stackTraceToString())
            return LoadResult.Error(e)
        }
    }
}