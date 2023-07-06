package com.andreformosa.moviedb.data.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("overview")
    val overview: String,
    @SerialName("poster_path")
    val poster: String,
    @SerialName("title")
    val title: String,
    @SerialName("vote_average")
    val rating: Double
)
