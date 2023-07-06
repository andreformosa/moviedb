package com.andreformosa.moviedb.data.model.remote

import com.andreformosa.moviedb.data.api.TheMovieDatabaseService
import com.andreformosa.moviedb.data.model.local.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("overview")
    val overview: String,
    @SerialName("poster_path")
    val poster: String,
    @SerialName("release_date")
    val year: String,
    @SerialName("title")
    val title: String,
)

fun MovieResponse.toMovie(page: Int) = Movie(
    id = this.id,
    title = this.title,
    year = this.year.substring(0, 4).toInt(),
    poster = "${TheMovieDatabaseService.POSTER_PATH}${this.poster}",
    overview = overview,
    page = page,
)
