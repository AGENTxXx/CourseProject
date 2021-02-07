package com.smog.courseproject

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.smog.courseproject.data.Movie
import com.smog.courseproject.data.MovieDb
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FragmentMoviesList.CardFragmentClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        getPreferences(MODE_PRIVATE)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.activity_main_fl, FragmentMoviesList.newInstance(), "first")
                .commit()
        }
    }

    override fun cardClick(movie: MovieDb) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .add(R.id.activity_main_fl, FragmentMoviesDetails.newInstance(movie), "second")
            .commit()
    }
}