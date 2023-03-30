package com.amirul.movie.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.amirul.movie.R
import com.amirul.movie.data.Movie
import com.squareup.picasso.Picasso

class MovieDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        // Get the intent extras
        val movieTitle = intent.getStringExtra("movieTitle")
        val moviePlot = intent.getStringExtra("moviePlot")
        val movieDirector = intent.getStringExtra("movieDirector")
        val movieStars = intent.getStringExtra("movieStars")
        val moviePoster = intent.getStringExtra("movieBackdrop")
        val movieDate = intent.getStringExtra("releaseDate")
        val voteAverage = intent.getStringExtra("voteAverage")
        val voteCount = intent.getStringExtra("voteCount")
        val moviePoster2 = intent.getStringExtra("moviePoster")


        // Set the text views with the movie details
        findViewById<TextView>(R.id.title_text_view).text = movieTitle
        findViewById<TextView>(R.id.title_text_view2).text = movieTitle
        findViewById<TextView>(R.id.plot_text_view).text = moviePlot
        findViewById<TextView>(R.id.director_text_view).text = movieDirector
        findViewById<TextView>(R.id.stars_text_view).text = movieStars
        findViewById<TextView>(R.id.release_date_text_view).text = movieDate
        findViewById<TextView>(R.id.vote_count_text_view).text = voteCount
        findViewById<TextView>(R.id.vote_average_text_view).text = voteAverage

        // Load the movie poster image using Picasso
        Picasso.get().load(moviePoster).into(findViewById<ImageView>(R.id.poster_image_view))
        Picasso.get().load(moviePoster2).into(findViewById<ImageView>(R.id.poster_image_view2))

    }
}





