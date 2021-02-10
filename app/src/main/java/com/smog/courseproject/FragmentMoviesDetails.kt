package com.smog.courseproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smog.courseproject.MainActivity.Companion.dbApp
import com.smog.courseproject.data.CastEntity
import com.smog.courseproject.data.MovieCastEntity
import com.smog.courseproject.data.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMoviesDetails.newInstance] factory method to
 * create an instance of this fragment.
 */


class FragmentMoviesDetails() : Fragment() {

    private var adapter = ActorListAdapter()
    private val viewModel: MoviesDetailsViewModel by viewModels {
        MoviesDetailsViewModelFactory(arguments.movie)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.movieLiveData.observe(viewLifecycleOwner, ::initMovieData)

        viewModel.getCredits(arguments.movie.id!!).observe(viewLifecycleOwner, ::showMovieCredits)

        view.findViewById<TextView>(R.id.activity_movie_details_tv_back).setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun showMovieCredits(credits:List<CastEntity>) {
        val view = requireView()
        val cast: TextView = view.findViewById(R.id.activity_movie_details_tv_cast)

        cast.visibility = if (credits.isEmpty()) {
            View.INVISIBLE
        } else {
            adapter.bindActors(credits)
            View.VISIBLE
        }
    }

    private fun initMovieData(movie: MovieEntity) {
        val view = requireView()
        val imgPoster: ImageView = view.findViewById(R.id.activity_movie_details_img_preview)
        val minimumAge: TextView = view.findViewById(R.id.fragment_movie_list_tv_age_rating)
        val title: TextView = view.findViewById(R.id.activity_movie_details_tv_title)
        val genres: TextView = view.findViewById(R.id.activity_movie_details_tv_genres)
        val reviews: TextView = view.findViewById(R.id.activity_movie_details_tv_reviews)
        val description: TextView = view.findViewById(R.id.activity_movie_details_tv_description)
        val stars: RatingBar = view.findViewById(R.id.activity_movie_details_rb_rating)

        title.text = movie.title
        stars.rating = (movie.voteAverage?.div(2))?.toFloat()!!
        minimumAge.text = getString(R.string.movie_minimum_age, if (movie.adult!!) 18 else 13)
        genres.text = movie.genreIds?.map { FragmentMoviesList.genres[it] }?.joinToString(", ")
        reviews.text = getString(R.string.movie_reviews_count, movie.voteCount)
        description.text = movie.overview

        Glide.with(requireContext())
            .load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/" + movie.backdropPath)
            .placeholder(R.drawable.film_poster_detail_dummy)
            .error(R.drawable.film_poster_detail_dummy)
            .into(imgPoster)
        val rv: RecyclerView = view.findViewById(R.id.fragment_movies_details_actors_rv)
        rv.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance(movie: MovieEntity) =
            FragmentMoviesDetails().apply {
                arguments = Bundle().also {
                    it.movie = movie
                }
            }

        var Bundle?.movie: MovieEntity
            set(value) {
                this?.putParcelable("movie", value)
            }
            get() {
                return this?.getParcelable("movie")
                    ?: throw IllegalArgumentException("Provide movie")
            }
    }

}