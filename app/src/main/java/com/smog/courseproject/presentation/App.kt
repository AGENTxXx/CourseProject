package com.smog.courseproject.presentation

import android.app.Application
import android.util.Log
import androidx.room.Room
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.smog.courseproject.data.local.AppDatabase
import com.smog.courseproject.presentation.worker.SyncMoviesWorker
import java.util.concurrent.TimeUnit

class App : Application() {

    companion object {
        lateinit var dbApp: AppDatabase
    }


    override fun onCreate() {
        super.onCreate()

        //TODO запихнул инициализацию в App вместо MainActivity
        dbApp = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database"
        )
            .allowMainThreadQueries()
            .build()


        val mWorkManager = WorkManager.getInstance(applicationContext)
        val constraints: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()
        val myWorkRequest: PeriodicWorkRequest = PeriodicWorkRequest.Builder(
            SyncMoviesWorker::class.java,
            8,
            TimeUnit.HOURS
        )
            .setInitialDelay(8, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        mWorkManager.enqueue(myWorkRequest)

        mWorkManager.getWorkInfoByIdLiveData(myWorkRequest.id).observeForever {
            if (it != null) {
                Log.d("periodicWorkRequest", "Status changed to ${it.state.isFinished}")
            }
        }
    }
}