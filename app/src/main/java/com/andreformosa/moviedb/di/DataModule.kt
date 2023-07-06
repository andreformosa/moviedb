package com.andreformosa.moviedb.di

import com.andreformosa.moviedb.data.movies.MoviesRepository
import com.andreformosa.moviedb.data.movies.OfflineFirstMoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataModule {

    @Binds
    abstract fun bindMoviesRepository(bind: OfflineFirstMoviesRepository): MoviesRepository
}
