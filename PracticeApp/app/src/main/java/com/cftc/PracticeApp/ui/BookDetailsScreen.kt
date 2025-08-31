package com.cftc.PracticeApp.ui

import androidx.compose.foundation.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.cftc.PracticeApp.viewmodel.BookDetailsViewModel

@Composable
fun BookDetailsScreen() {
    val viewModel: BookDetailsViewModel = viewModel()
    val state: BookDetailsUiState by viewModel.state.collectAsState()

    LaunchedEffect(Unit) { viewModel.getBookDetails() }

    Box(
        Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoadingDetails) {
            CircularProgressIndicator()
        } else {
            Card(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                LazyColumn(
                    Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        // Hero image
                        AsyncImage(
                            model = state.bookDetails?.imageUrl,
                            contentDescription = "Cover of ${state.bookDetails?.title}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    item {
                        // Title + author
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                state.bookDetails?.title.orEmpty(),
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                state.bookDetails?.author.orEmpty(),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    item {
                        // Rating row
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFC107)
                            )
                            Text("${state.bookDetails?.rating} • ${state.bookDetails?.reviewsCount} reviews")
                        }
                    }

                    item {
                        // Genres
                        GenresFlowRow(state.bookDetails?.genresList ?: emptyList())
                    }

                    item {
                        // Action buttons
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = { /* sample */ }, Modifier.weight(1f)) {
                                Text("Read Sample")
                            }
                            OutlinedButton(onClick = { /* add/remove */ }, Modifier.weight(1f)) {
                                Text("Add to List")
                            }
                        }
                    }

                    item { HorizontalDivider() }


                    item {
                        // Description
                        Column {
                            Text("About this book", style = MaterialTheme.typography.titleMedium)
                            Text(
                                state.bookDetails?.description.orEmpty(),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }

}

@Composable
private fun GenresFlowRow(genres: List<String>) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        genres.forEach { genre ->
            Surface(
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = genre,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}