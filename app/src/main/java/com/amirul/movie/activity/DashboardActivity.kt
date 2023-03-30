package com.amirul.movie.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.amirul.movie.BuildConfig
import com.amirul.movie.R
import com.amirul.movie.adapter.MovieAdapter
import com.amirul.movie.data.Movie
import com.amirul.movie.data.MovieResponse
import com.amirul.movie.data.TmdbApi
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DashboardActivity : AppCompatActivity() {

    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var loadingIndicator: ProgressBar


    private var currentPage = 1
    private var totalPages = 1
    private var isLoading = false

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        loadingIndicator = findViewById(R.id.loading_indicator)
        loadingIndicator.visibility = View.GONE

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            currentPage = 1
            totalPages = 1
            movieAdapter.clearMovies()
            loadMoreMovies()
        }

        val layoutManager = GridLayoutManager(this, 2)
        movieRecyclerView = findViewById(R.id.movie_recycler_view)
        movieRecyclerView.layoutManager = layoutManager

        movieAdapter = MovieAdapter(this)
        movieRecyclerView.adapter = movieAdapter

        movieRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!isLoading && currentPage < totalPages && !recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    currentPage++
                    loadMoreMovies()
                }
            }
        })

        loadMoreMovies()
    }

    private fun loadMoreMovies() {
        isLoading = true
        loadingIndicator.visibility = View.VISIBLE
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val tmdbApi = retrofit.create(TmdbApi::class.java)
        val call = tmdbApi.getPopularMovies("5c275d1bd7c1e6269e9c2140913ceacf", currentPage)
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                isLoading = false
                loadingIndicator.visibility = View.GONE
                swipeRefreshLayout.isRefreshing = false
                if (response.isSuccessful) {
                    val movieResults = response.body()?.getAsJsonArray("results")
                    val movies = Gson().fromJson<List<Movie>>(
                        movieResults,
                        object : TypeToken<List<Movie>>() {}.type
                    )
                    totalPages = response.body()?.get("total_pages")?.asInt ?: 1
                    movieAdapter.addMovies(movies)
                } else {
                    Toast.makeText(this@DashboardActivity, "Failed to get movies", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                isLoading = false
                swipeRefreshLayout.isRefreshing = false
                Toast.makeText(this@DashboardActivity, "Failed to get movies", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

