package com.smog.courseproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smog.courseproject.data.Movie
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.smog.courseproject.data.MovieDb

class MovieListAdapter(private val onMovieClicked: (MovieDb) -> Unit): PagingDataAdapter<MovieDb,MovieViewHolder>(moviewItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie,parent,false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(getItem(position)!!)
        holder.itemView.setOnClickListener {
            onMovieClicked(getItem(position)!!)
        }
    }

    companion object {
        private val moviewItemCallback = object: DiffUtil.ItemCallback<MovieDb>() {
            override fun areItemsTheSame(oldItem: MovieDb, newItem: MovieDb): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieDb, newItem: MovieDb): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.findViewById(R.id.fragment_movie_list_tv_movie_title)
    private val genres: TextView = itemView.findViewById(R.id.fragment_movie_list_tv_movie_genres)
    private val reviews: TextView = itemView.findViewById(R.id.fragment_movie_list_tv_movie_reviews)
    private val length: TextView = itemView.findViewById(R.id.fragment_movie_list_tv_movie_length)
    private val minimumAge: TextView = itemView.findViewById(R.id.fragment_movie_list_tv_age_rating)
    private val stars: RatingBar = itemView.findViewById(R.id.fragment_movies_list_rb_rating)
    private val like:ImageView = itemView.findViewById(R.id.fragment_movie_list_img_movie_like)



    val img: ImageView = itemView.findViewById(R.id.fragment_movie_list_img_poster)

    fun onBind(movie:MovieDb) {
        with(itemView.context) {
            minimumAge.text = getString(R.string.movie_minimum_age,if (movie.adult!!) 18 else 13)
            reviews.text = getString(R.string.movie_reviews_count,movie.voteCount)
            length.text = getString(R.string.movie_list_movie_length,0)
            //length.text = getString(R.string.movie_list_movie_length,movie.runtime)

            Glide.with(this@with)
                .load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/" + movie.posterPath)
                .placeholder(R.drawable.film_poster_dummy)
                .error(R.drawable.film_poster_dummy)
                .into(img)
        }

        title.text = movie.title
        stars.rating = (movie.voteAverage?.div(2))?.toFloat()!!
        genres.text = movie.genreIds?.map { FragmentMoviesList.genres[it] }?.joinToString(", ")
        //genres.text = movie.genres.joinToString(", ") { it.name }
    }
}

