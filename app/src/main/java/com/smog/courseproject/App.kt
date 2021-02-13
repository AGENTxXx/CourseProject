package com.smog.courseproject

import android.app.Application
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val mWorkManager = WorkManager.getInstance()
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