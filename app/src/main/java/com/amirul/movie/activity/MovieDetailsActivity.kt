package com.amirul.movie.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.amirul.movie.R
import com.amirul.movie.data.Movie

class MovieDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        // Get the intent extras
        val movieTitle = intent.getStringExtra("movieTitle")
        val moviePlot = intent.getStringExtra("moviePlot")
        val movieDirector = intent.getStringExtra("movieDirector")
        val movieStars = intent.getStringExtra("movieStars")

        // Set the text views with the movie details
        findViewById<TextView>(R.id.title_text_view).text = movieTitle
        findViewById<TextView>(R.id.plot_text_view).text = moviePlot
        findViewById<TextView>(R.id.director_text_view).text = movieDirector
        findViewById<TextView>(R.id.stars_text_view).text = movieStars
    }
}




