package com.smog.courseproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(),FragmentMoviesList.CardFragmentClickListener {

    //private lateinit var fragmentMoviesList:FragmentMoviesList
    //private lateinit var fragmentMoviesDetails:FragmentMoviesDetails
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            //val fragmentMoviesList = FragmentMoviesList.newInstance()//.apply { setClickListener(this@MainActivity) }
            supportFragmentManager.beginTransaction()
                .add(R.id.activity_main_fl, FragmentMoviesList.newInstance(),"first")
                .commit()
        }
        /*
        else {
            val fragmentMoviesList = supportFragmentManager.findFragmentByTag("first") as FragmentMoviesList
            //fragmentMoviesList.setClickListener(this)

            if (supportFragmentManager.findFragmentByTag("second") != null) {
                val fragmentMoviesDetails = supportFragmentManager.findFragmentByTag("second") as FragmentMoviesDetails
                //fragmentMoviesDetails.setClickListener(this)
            }
        }
        */
    }

    override fun cardClick() {
        //val fragmentMoviesDetails = FragmentMoviesDetails.newInstance()//().apply { setClickListener(this@MainActivity) }
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .add(R.id.activity_main_fl, FragmentMoviesDetails.newInstance(),"second")
            .commit()
    }

    /*
    override fun doBack() {
        onBackPressed()
    }
    */


}