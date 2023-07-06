package com.andreformosa.moviedb.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.andreformosa.moviedb.data.model.local.Movie
import com.andreformosa.moviedb.ui.composables.pullrefresh.PullRefreshIndicator
import com.andreformosa.moviedb.ui.composables.pullrefresh.pullRefresh
import com.andreformosa.moviedb.ui.composables.pullrefresh.rememberPullRefreshState
import com.andreformosa.moviedb.ui.theme.ThemeGrey
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    val movies = viewModel.movies.collectAsLazyPagingItems()

    var refreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(refreshing, { movies.refresh() })

    val openBottomSheetState by viewModel.openBottomSheet.collectAsStateWithLifecycle()
    val bottomSheetUiState by viewModel.bottomSheetContent.collectAsStateWithLifecycle()

    movies.apply {
        // Set refresh state whenever [movies] paging data is refreshed
        refreshing = when (loadState.refresh) {
            is LoadState.Loading -> true
            else -> false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        MoviesList(
            movies = movies,
            onItemClick = viewModel::onItemClick,
            modifier = Modifier.fillMaxSize()
        )

        if (openBottomSheetState) {
            MovieDetailsBottomSheet(
                bottomSheetUiState = bottomSheetUiState,
                onDismissed = viewModel::onBottomSheetDismissed,
            )
        }

        PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
private fun MoviesList(
    movies: LazyPagingItems<Movie>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(
            count = movies.itemCount,
            key = movies.itemKey { it.id },
        ) { index ->
            movies[index]?.let { movie ->
                MovieListItem(
                    movie = movie,
                    onClick = onItemClick
                )
            }
        }
    }
}

@Composable
private fun MovieListItem(
    movie: Movie,
    onClick: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                enabled = true,
                onClick = { onClick(movie.id) }
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Card {
            var showShimmer by remember { mutableStateOf(true) }
            AsyncImage(
                model = movie.poster,
                contentScale = ContentScale.Crop,
                contentDescription = movie.title,
                modifier = Modifier
                    .aspectRatio(0.66f)
                    .placeholder(
                        visible = showShimmer,
                        color = ThemeGrey,
                        highlight = PlaceholderHighlight.shimmer()
                    ),
                onSuccess = { showShimmer = false }
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${movie.title} (${movie.year})",
            color = ThemeGrey,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 2,
            overflow = TextOverflow.Clip
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieDetailsBottomSheet(
    bottomSheetUiState: BottomSheetUiState,
    onDismissed: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
) {
    ModalBottomSheet(
        onDismissRequest = onDismissed,
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        modifier = modifier,
    ) {
        when (bottomSheetUiState) {
            is BottomSheetUiState.Loaded -> {
                val movie = remember { bottomSheetUiState.movie }

                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = movie.poster,
                        contentScale = ContentScale.Crop,
                        contentDescription = movie.title,
                        modifier = Modifier
                            .aspectRatio(0.66f)
                            .weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(2f)) {
                        Text(
                            text = movie.title,
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row {
                            Text(
                                text = "${movie.year}",
                                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 11.sp),
                                maxLines = 1,
                            )
                            movie.rating?.let {
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "★${String.format("%.1f", movie.rating)}",
                                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 11.sp),
                                    maxLines = 1,
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = movie.overview,
                            color = ThemeGrey, // Ideally, get from MaterialTheme
                            style = MaterialTheme.typography.labelSmall,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }

            BottomSheetUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
