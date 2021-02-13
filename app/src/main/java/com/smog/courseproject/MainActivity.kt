package com.smog.courseproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.smog.courseproject.data.MovieEntity
import com.smog.courseproject.database.AppDatabase


class MainActivity : AppCompatActivity(), FragmentMoviesList.CardFragmentClickListener {

    companion object {
        lateinit var dbApp: AppDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        dbApp = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database"
        )
            .allowMainThreadQueries()
            .build()

        getPreferences(MODE_PRIVATE)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.activity_main_fl, FragmentMoviesList.newInstance(), "first")
                .commit()
        }
    }

    override fun cardClick(movie: MovieEntity) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .add(R.id.activity_main_fl, FragmentMoviesDetails.newInstance(movie), "second")
            .commit()
    }
}