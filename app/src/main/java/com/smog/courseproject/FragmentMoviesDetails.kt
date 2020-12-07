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


/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMoviesDetails.newInstance] factory method to
 * create an instance of this fragment.
 */


class FragmentMoviesDetails() : Fragment() {

    private lateinit var movie:Movie
    private lateinit var rv: RecyclerView
    private var adapter = ActorListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getParcelable("movie")!!
        }
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



        val imgPoster = view.findViewById<ImageView>(R.id.activity_movie_details_img_preview)
        val rating = view.findViewById<TextView>(R.id.fragment_movie_list_tv_age_rating)
        val title = view.findViewById<TextView>(R.id.activity_movie_details_tv_title)
        val genres: TextView = view.findViewById(R.id.activity_movie_details_tv_genres)
        val reviews = view.findViewById<TextView>(R.id.activity_movie_details_tv_reviews)
        val description = view.findViewById<TextView>(R.id.activity_movie_details_tv_description)
        var stars: RatingBar = view.findViewById(R.id.activity_movie_details_rb_rating)

        title.text = movie.title
        stars.rating = movie.stars
        rating.text = movie.rating
        genres.text = movie.genres
        reviews.text = requireContext().getString(
            R.string.movie_reviews_count,
            movie.reviewCount.toString()
        )
        description.text = movie.description

        val imgPreview = requireContext().resources.getIdentifier(
            movie.detailLink ?: movie.posterLink,
            "drawable",
            requireContext().packageName
        )
        imgPoster.setImageResource(imgPreview)

        rv = view.findViewById(R.id.fragment_movies_details_actors_rv)
        rv.adapter = adapter
        updateData()


        view.findViewById<TextView>(R.id.activity_movie_details_tv_back).setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun updateData() {
        adapter.bindActors(ActorDataSource().getActors())
        adapter.notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun newInstance(movie: Movie) =
            FragmentMoviesDetails().apply {
                arguments = Bundle().apply {
                    putParcelable("movie",movie)
                }
            }

    }

}