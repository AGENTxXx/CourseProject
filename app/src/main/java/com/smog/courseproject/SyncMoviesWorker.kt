package com.smog.courseproject

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.smog.courseproject.MainActivity.Companion.dbApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SyncMoviesWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        println("WORK IS STARTED")
        val movieDao = dbApp.movieDao()
        CoroutineScope(Dispatchers.IO).launch {
            for (pageNum in 1..10) {
                val response = RetrofitModule.moviesApi.getMovies(pageNum)
                val movies = response.body()!!.results!!
                movieDao?.insertAll(movies)
            }
        }

        return Result.success()
    }
}