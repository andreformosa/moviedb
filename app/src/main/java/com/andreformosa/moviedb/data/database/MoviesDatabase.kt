package com.andreformosa.moviedb.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andreformosa.moviedb.data.movies.MoviesDao
import com.andreformosa.moviedb.data.movies.RemoteKeysDao
import com.andreformosa.moviedb.data.model.local.Movie
import com.andreformosa.moviedb.data.model.local.RemoteKeys

@Database(
    entities = [Movie::class, RemoteKeys::class],
    version = 1,
)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun getMoviesDao(): MoviesDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
}
