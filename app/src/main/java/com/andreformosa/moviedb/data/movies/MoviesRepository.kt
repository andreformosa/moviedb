package com.andreformosa.moviedb.data.movies

interface MoviesRepository {

    suspend fun getMovieDetails(movieId: Int): MovieDetailsResult
}
