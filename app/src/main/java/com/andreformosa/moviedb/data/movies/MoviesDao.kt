package com.andreformosa.moviedb.data.movies

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreformosa.moviedb.data.model.local.Movie

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    // Update rating column only
    @Query("UPDATE movies SET rating=:rating WHERE id = :id")
    suspend fun update(id: Int, rating: Double?)

    @Query("SELECT * FROM movies ORDER BY page")
    fun getMovies(): PagingSource<Int, Movie>

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovieById(id: Int): Movie?

    @Query("DELETE FROM movies")
    suspend fun clearAllMovies()
}
