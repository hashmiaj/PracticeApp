package com.cftc.PracticeApp.ui

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.cftc.PracticeApp.model.BookModel
import com.cftc.PracticeApp.viewmodel.MyLibraryViewModel

@Composable
fun MyLibraryScreen() {
    val viewModel: MyLibraryViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) { viewModel.getMyLibraryBooks() }

    val listState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }

    Box(Modifier.fillMaxSize()) {
        val latest by rememberUpdatedState(state)

        LazyColumn(Modifier.fillMaxSize(), state = listState) {
            items(
                count = latest.booksList?.size ?: 0,
                key = { index -> latest.booksList?.get(index)?.id ?: 0 },
                contentType = { _ -> "books" },
            ) { index ->
                val book: BookModel? = latest.booksList?.get(index)
                if (book != null) {
                    LibraryBook(book)
                }
            }
            if (latest.isLoadingBooks) {
                item(key = "append_loading_footer", contentType = "loadingFooter") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }

    }
}

@Composable
fun LibraryBook(book: BookModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.padding(4.dp)) {
                AsyncImage(
                    model = book.imageUrl,
                    contentDescription = "Book cover of ${book.title}",
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                Modifier
                    .padding(horizontal = 4.dp)
                    .weight(1f)
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = book.author,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = book.description,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            val (isFavorite, setIsFavorite) = rememberSaveable(book.id) {
                mutableStateOf(book.isFavorite)
            }
            IconToggleButton(
                checked = isFavorite, onCheckedChange = { checked -> setIsFavorite(checked) }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.StarBorder,
                    contentDescription = if (isFavorite) "Unfavorite" else "Favorite",
                    tint = if (isFavorite) Color(0xFFFFBF02) else MaterialTheme.colorScheme.onSurface
                )
            }

        }

        HorizontalDivider()
    }
}
