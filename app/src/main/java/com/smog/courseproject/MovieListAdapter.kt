package com.smog.courseproject

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
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
    private val rating: TextView = itemView.findViewById(R.id.fragment_movie_list_tv_age_rating)
    private val stars: RatingBar = itemView.findViewById(R.id.fragment_movies_list_rb_rating)
    private val like:ImageView = itemView.findViewById(R.id.fragment_movie_list_img_movie_like)



    val img: ImageView = itemView.findViewById(R.id.fragment_movie_list_img_poster)

    fun onBind(movie:Movie) {
        title.text = movie.title
        stars.rating = movie.stars
        rating.text = movie.rating
        genres.text = movie.genres
        reviews.text = context.getString(R.string.movie_reviews_count,movie.reviewCount)
        length.text = context.getString(R.string.movie_list_movie_length,movie.length)

        if (movie.isLiked) {
            ImageViewCompat.setImageTintList(like, ColorStateList.valueOf(ContextCompat.getColor(context,R.color.red_radical)))
        }
        else {
            like.clearColorFilter()
        }

        val imgPreview = getDrawableFromName(context,movie.posterLink)
        img.setImageResource(imgPreview)
    }
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context