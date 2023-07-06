package com.andreformosa.moviedb.data.api

import com.andreformosa.moviedb.BuildConfig
import com.andreformosa.moviedb.data.model.remote.DiscoverMoviesResponse
import com.andreformosa.moviedb.data.model.remote.MovieDetailsResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDatabaseService {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/"
        const val POSTER_PATH = "https://image.tmdb.org/t/p/original"
    }

    @GET("/3/discover/movie?api_key=${BuildConfig.TMDB_API_KEY}")
    suspend fun discoverMovies(
        @Query("page") page: Int
    ): ApiResponse<DiscoverMoviesResponse>

    @GET("/3/movie/{movie_id}?api_key=${BuildConfig.TMDB_API_KEY}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): ApiResponse<MovieDetailsResponse>
}
