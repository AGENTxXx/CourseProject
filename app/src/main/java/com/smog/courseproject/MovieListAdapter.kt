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

class MovieListAdapter: RecyclerView.Adapter<MovieViewHolder>() {

    private var movies:List<Movie> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie,parent,false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBlind(movies[position])
        holder.itemView.setOnClickListener {
            (holder.itemView.context as FragmentMoviesList.CardFragmentClickListener).cardClick(movies[position])
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun bindMovies(newMovies:List<Movie>) {
        movies = newMovies
    }
}

class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    companion object {
        private val imageOption = RequestOptions()
            .placeholder(R.drawable.ic_avatar_placeholder)
            .fallback(R.drawable.ic_avatar_placeholder)
            .circleCrop()
    }

    val title: TextView = itemView.findViewById(R.id.fragment_movie_list_tv_movie_title)
    val genres: TextView = itemView.findViewById(R.id.fragment_movie_list_tv_movie_genres)
    var reviews: TextView = itemView.findViewById(R.id.fragment_movie_list_tv_movie_reviews)
    var length: TextView = itemView.findViewById(R.id.fragment_movie_list_tv_movie_length)
    var rating: TextView = itemView.findViewById(R.id.fragment_movie_list_tv_age_rating)
    var stars: RatingBar = itemView.findViewById(R.id.fragment_movies_list_rb_rating)
    var like:ImageView = itemView.findViewById(R.id.fragment_movie_list_img_movie_like)



    val img: ImageView = itemView.findViewById(R.id.fragment_movie_list_img_poster)

    fun onBlind(movie:Movie) {
        title.text = movie.title
        stars.rating = movie.stars
        rating.text = movie.rating
        genres.text = movie.genres
        reviews.text = context.getString(R.string.movie_reviews_count,movie.reviewCount.toString())
        length.text = context.getString(R.string.movie_list_movie_length,movie.length.toString())

        if (movie.isLiked) {
            ImageViewCompat.setImageTintList(like, ColorStateList.valueOf(ContextCompat.getColor(context,R.color.red_radical)))
        }
        else {
            like.clearColorFilter()
        }

        val imgPreview = context.resources.getIdentifier(
            movie.posterLink,
            "drawable",
            context.packageName)
        img.setImageResource(imgPreview)
    }
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context