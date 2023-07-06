package com.andreformosa.moviedb.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.andreformosa.moviedb.data.api.TheMovieDatabaseService
import com.andreformosa.moviedb.data.movies.MoviesRemoteMediator
import com.andreformosa.moviedb.data.database.MoviesDatabase
import com.andreformosa.moviedb.data.model.local.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagedMoviesUseCase @Inject constructor(
    private val moviesDatabase: MoviesDatabase,
    private val service: TheMovieDatabaseService,
) {

    @OptIn(ExperimentalPagingApi::class)
    operator fun invoke(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = 10,
                initialLoadSize = PAGE_SIZE + 10,
            ),
            pagingSourceFactory = {
                moviesDatabase.getMoviesDao().getMovies()
            },
            remoteMediator = MoviesRemoteMediator(
                moviesDatabase,
                service,
            )
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
