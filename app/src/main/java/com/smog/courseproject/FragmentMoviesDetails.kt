package com.smog.courseproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.smog.courseproject.data.Movie
import com.smog.courseproject.domain.ActorDataSource
import java.lang.IllegalArgumentException


/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMoviesDetails.newInstance] factory method to
 * create an instance of this fragment.
 */


class FragmentMoviesDetails() : Fragment() {

    var Bundle.movie:Movie
    set(value) {
        this.putParcelable("movie",value)
    }
    get() {
        return  this.getParcelable("movie")
            ?: throw IllegalArgumentException("Provide movie")
    }

    private var adapter = ActorListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie: Movie = arguments?.movie!!

        val imgPoster:ImageView = view.findViewById(R.id.activity_movie_details_img_preview)
        val rating:TextView = view.findViewById(R.id.fragment_movie_list_tv_age_rating)
        val title:TextView = view.findViewById(R.id.activity_movie_details_tv_title)
        val genres: TextView = view.findViewById(R.id.activity_movie_details_tv_genres)
        val reviews:TextView = view.findViewById(R.id.activity_movie_details_tv_reviews)
        val description:TextView = view.findViewById(R.id.activity_movie_details_tv_description)
        val stars: RatingBar = view.findViewById(R.id.activity_movie_details_rb_rating)

        title.text = movie.title
        stars.rating = movie.stars
        rating.text = movie.rating
        genres.text = movie.genres
        reviews.text = requireContext().getString(
            R.string.movie_reviews_count,
            movie.reviewCount
        )
        description.text = movie.description

        val imgPreview = Utils.getDrawableFromName(requireContext(), movie.detailLink ?: movie.posterLink)
        imgPoster.setImageResource(imgPreview)

        val rv: RecyclerView = view.findViewById(R.id.fragment_movies_details_actors_rv)
        rv.adapter = adapter
        updateData()


        view.findViewById<TextView>(R.id.activity_movie_details_tv_back).setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun updateData() {
        adapter.bindActors(ActorDataSource().getActors())
    }

    companion object {
        @JvmStatic
        fun newInstance(movie: Movie) =
            FragmentMoviesDetails().apply {
                arguments = Bundle().also {
                    it.movie = movie
                }
            }

    }

}