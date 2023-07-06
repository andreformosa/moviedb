package com.andreformosa.moviedb.data.movies

import androidx.room.withTransaction
import com.andreformosa.moviedb.data.database.MoviesDatabase
import com.andreformosa.moviedb.data.model.local.Movie
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineFirstMoviesRepository @Inject constructor(
    private val remoteMoviesDataSource: RemoteMoviesDataSource,
    private val moviesDatabase: MoviesDatabase,
) : MoviesRepository {

    /**
     * Look for cached movie which contains all the required fields and return it.
     * Otherwise, fetch from network, update database and return updated entity.
     */
    override suspend fun getMovieDetails(movieId: Int): MovieDetailsResult {
        val movie = moviesDatabase.getMoviesDao().getMovieById(movieId)
        if (movie?.rating != null) {
            return MovieDetailsResult.Success(movie)
        }

        return when (val response = remoteMoviesDataSource.getMovieDetails(movieId)) {
            is ApiResponse.Success -> {
                val movieDetails = response.data
                var savedMovie: Movie? = null

                moviesDatabase.withTransaction {
                    // Update movie with rating field
                    moviesDatabase.getMoviesDao().update(movieId, movieDetails.rating)

                    // Fetch saved movie
                    savedMovie = moviesDatabase.getMoviesDao().getMovieById(movieId)
                }

                MovieDetailsResult.Success(
                    checkNotNull(savedMovie) { "Movie cannot be null after updating it" }
                )
            }

            is ApiResponse.Failure.Error,
            is ApiResponse.Failure.Exception -> MovieDetailsResult.GenericError
        }
    }
}
