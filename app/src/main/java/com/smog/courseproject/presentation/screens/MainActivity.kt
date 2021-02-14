package com.smog.courseproject.presentation.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.smog.courseproject.R
import com.smog.courseproject.data.MovieEntity
import com.smog.courseproject.data.local.AppDatabase
import com.smog.courseproject.presentation.screens.moviedetail.FragmentMoviesDetails
import com.smog.courseproject.presentation.screens.movielist.FragmentMoviesList


class MainActivity : AppCompatActivity(), FragmentMoviesList.CardFragmentClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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