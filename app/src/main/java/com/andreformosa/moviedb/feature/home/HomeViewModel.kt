package com.andreformosa.moviedb.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.andreformosa.moviedb.data.model.local.Movie
import com.andreformosa.moviedb.data.movies.MovieDetailsResult
import com.andreformosa.moviedb.data.movies.MoviesRepository
import com.andreformosa.moviedb.domain.GetPagedMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getPagedMoviesUseCase: GetPagedMoviesUseCase,
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    private val _openBottomSheet = MutableStateFlow(false)
    val openBottomSheet: StateFlow<Boolean> = _openBottomSheet

    private val _bottomSheetContent =
        MutableStateFlow<BottomSheetUiState>(BottomSheetUiState.Loading)
    val bottomSheetContent: StateFlow<BottomSheetUiState> = _bottomSheetContent

    val movies: Flow<PagingData<Movie>> = getPagedMoviesUseCase()

    fun onItemClick(id: Int) {
        _openBottomSheet.value = true
        _bottomSheetContent.value = BottomSheetUiState.Loading

        viewModelScope.launch {
            when (val response = moviesRepository.getMovieDetails(id)) {
                is MovieDetailsResult.Success -> {
                    _bottomSheetContent.value = BottomSheetUiState.Loaded(response.data)
                }

                MovieDetailsResult.GenericError -> {
                    // Note: Ideally, an error message would be shown here

                    // Clear bottom sheet just in case
                    onBottomSheetDismissed()
                }
            }
        }
    }

    fun onBottomSheetDismissed() {
        _openBottomSheet.value = false
    }
}

sealed interface BottomSheetUiState {
    object Loading : BottomSheetUiState
    data class Loaded(val movie: Movie) : BottomSheetUiState
}
