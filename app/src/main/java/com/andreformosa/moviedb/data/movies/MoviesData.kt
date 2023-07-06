package com.andreformosa.moviedb.data.movies

import com.andreformosa.moviedb.data.model.local.Movie

sealed interface MovieDetailsResult {
    data class Success(val data: Movie) : MovieDetailsResult
    object GenericError : MovieDetailsResult
}
