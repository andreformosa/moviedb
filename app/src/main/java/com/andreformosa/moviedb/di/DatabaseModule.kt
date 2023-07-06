package com.andreformosa.moviedb.di

import android.content.Context
import androidx.room.Room
import com.andreformosa.moviedb.data.movies.MoviesDao
import com.andreformosa.moviedb.data.movies.RemoteKeysDao
import com.andreformosa.moviedb.data.database.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideMoviesDatabase(
        @ApplicationContext context: Context
    ): MoviesDatabase {
        return Room
            .databaseBuilder(context, MoviesDatabase::class.java, "movies-database")
            .build()
    }

    @Singleton
    @Provides
    fun provideMoviesDao(moviesDatabase: MoviesDatabase): MoviesDao {
        return moviesDatabase.getMoviesDao()
    }

    @Singleton
    @Provides
    fun provideRemoteKeysDao(moviesDatabase: MoviesDatabase): RemoteKeysDao {
        return moviesDatabase.getRemoteKeysDao()
    }
}
