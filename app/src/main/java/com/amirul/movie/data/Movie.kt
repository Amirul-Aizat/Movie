package com.amirul.movie.data

import com.google.gson.annotations.SerializedName

data class Movie (

    @SerializedName("title")
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("director")
    val director: String?,
    @SerializedName("stars")
    val stars: List<String>?,
    @SerializedName("plot")
    val plot: String

)
