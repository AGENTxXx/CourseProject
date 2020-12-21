package com.smog.courseproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smog.courseproject.data.Movie

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

    override fun cardClick(movie: Movie) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .add(R.id.activity_main_fl, FragmentMoviesDetails.newInstance(movie), "second")
            .commit()
    }
}