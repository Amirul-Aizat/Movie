package com.amirul.movie.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amirul.movie.R
import com.amirul.movie.activity.MovieDetailsActivity
import com.amirul.movie.data.Movie
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

class MovieAdapter(private val context: Context) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var movies: MutableList<Movie> = mutableListOf()

    fun setMovies(movies: List<Movie>) {
        this.movies = movies.toMutableList()
        notifyDataSetChanged()
    }

    fun addMovies(moreMovies: List<Movie>) {
        val startPosition = movies.size
        movies.addAll(moreMovies)
        notifyItemRangeInserted(startPosition, moreMovies.size)
    }

    fun clearMovies() {
        this.movies.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.movieTitle.text = movie.title
        val picasso: RequestCreator = Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
        picasso.resize(670, 950)
        picasso.centerCrop()
        picasso.into(holder.posterImageView)

        holder.itemView.setOnClickListener {
            // Launch new activity with selected movie details
            val intent = Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra("movieTitle", movie.title)
                putExtra("moviePlot", movie.overview)
                putExtra("movieDirector", movie.director?:"")
                putExtra("movieStars", movie.stars?.joinToString() ?: "")
                putExtra("moviePoster", "https://image.tmdb.org/t/p/w500" + movie.posterPath)
                putExtra("movieBackdrop", "https://image.tmdb.org/t/p/w500" + movie.backdropPath)
                putExtra("releaseDate", movie.releaseDate)
                putExtra("voteCount", movie.voteCount)
                putExtra("voteAverage", movie.voteAverage)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImageView: ImageView = itemView.findViewById(R.id.poster_image_view)
        val movieTitle: TextView = itemView.findViewById(R.id.movie_title)
    }
}

