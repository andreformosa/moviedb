package com.andreformosa.moviedb.di

import com.andreformosa.moviedb.data.movies.RemoteMoviesDataSource
import com.andreformosa.moviedb.data.movies.RetrofitMoviesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkModuleBinds {

    @Binds
    abstract fun bindRemoteMoviesDataSource(bind: RetrofitMoviesDataSource): RemoteMoviesDataSource
}
