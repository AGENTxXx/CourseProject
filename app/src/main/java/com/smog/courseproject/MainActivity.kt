package com.smog.courseproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(),FragmentMoviesList.CardFragmentClickListener, FragmentMoviesDetails.OnBackPressedListener {

    private lateinit var fragmentMoviesList:FragmentMoviesList
    private lateinit var fragmentMoviesDetails:FragmentMoviesDetails
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            fragmentMoviesList = FragmentMoviesList().apply { setClickListener(this@MainActivity) }
            supportFragmentManager.beginTransaction()
                .add(R.id.activity_main_fl, fragmentMoviesList,"first")
                .commit()
        }
        else {
            fragmentMoviesList = supportFragmentManager.findFragmentByTag("first") as FragmentMoviesList
            fragmentMoviesList.setClickListener(this)

            if (supportFragmentManager.findFragmentByTag("second") != null) {
                fragmentMoviesDetails = supportFragmentManager.findFragmentByTag("second") as FragmentMoviesDetails
                fragmentMoviesDetails.setClickListener(this)
            }
        }
    }

    override fun cardClick() {
        fragmentMoviesDetails = FragmentMoviesDetails().apply { setClickListener(this@MainActivity) }
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .add(R.id.activity_main_fl, fragmentMoviesDetails,"second")
            .commit()
    }

    override fun doBack() {
        onBackPressed()
    }


}