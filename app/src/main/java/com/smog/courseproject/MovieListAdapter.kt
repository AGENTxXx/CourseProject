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

class MovieListAdapter(private var movies:List<Movie> = listOf(), private val onMovieClicked: (Movie) -> Unit): RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie,parent,false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(movies[position])
        holder.itemView.setOnClickListener {
            onMovieClicked(movies[position])
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun bindMovies(newMovies:List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
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

    fun onBind(movie:Movie) {
        with(itemView.context) {
            minimumAge.text = getString(R.string.movie_minimum_age,movie.minimumAge)
            reviews.text = getString(R.string.movie_reviews_count,movie.numberOfRatings)
            length.text = getString(R.string.movie_list_movie_length,movie.runtime)

            Glide.with(this@with)
                .load(movie.poster)
                .placeholder(R.drawable.film_poster_dummy)
                .error(R.drawable.film_poster_dummy)
                .into(img)
        }

        title.text = movie.title
        stars.rating = movie.ratings
        genres.text = movie.genres.joinToString(", ") { it.name }
    }
}
