package com.andreformosa.moviedb.data.movies

import com.andreformosa.moviedb.data.model.remote.MovieDetailsResponse
import com.skydoves.sandwich.ApiResponse

interface RemoteMoviesDataSource {

    suspend fun getMovieDetails(movieId: Int): ApiResponse<MovieDetailsResponse>
}
