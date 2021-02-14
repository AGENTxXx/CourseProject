package com.smog.courseproject.presentation.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.smog.courseproject.data.network.RetrofitModule
import com.smog.courseproject.presentation.App.Companion.dbApp

class SyncMoviesWorker(
    context: Context, params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        println("WORK IS STARTED")
        val movieDao = dbApp.movieDao()

        for (pageNum in 1..10) {
            val response = RetrofitModule.moviesApi.getMovies(pageNum)
            val movies = response.body()!!.results!!
            movieDao.insertAll(movies)
        }

        return Result.success()
    }
}