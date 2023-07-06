package com.andreformosa.moviedb.data.movies

import com.andreformosa.moviedb.data.api.TheMovieDatabaseService
import com.andreformosa.moviedb.data.model.remote.MovieDetailsResponse
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class RetrofitMoviesDataSource @Inject constructor(
    private val service: TheMovieDatabaseService,
) : RemoteMoviesDataSource {

    override suspend fun getMovieDetails(movieId: Int): ApiResponse<MovieDetailsResponse> {
        return service.getMovieDetails(movieId)
    }
}
