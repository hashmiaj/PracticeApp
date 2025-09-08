package com.cftc.PracticeApp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cftc.PracticeApp.model.Book
import com.cftc.PracticeApp.model.BookCategory
import com.cftc.PracticeApp.viewmodel.LibraryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen() {
    val vm: LibraryViewModel = viewModel()
    val state: LibraryUiState by vm.state.collectAsState()

    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { TopAppBar({ Text("My Library") }) },
        snackbarHost = { SnackbarHost(snackbar) }) { inner ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(inner),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Search bar
            OutlinedTextField(
                value = state.inputQuery,
                onValueChange = vm::onQueryUpdated,
                placeholder = { Text("Search by title or author") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )

            // Filters
            FilterChips(state.selected, vm::onCategorySelected)

            // List
            when {
                state.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                state.showEmpty -> {
                    EmptyScreen()
                }

                else -> {
                    val sectionsList = listOf(
                        BookCategory.Reading, BookCategory.Completed, BookCategory.Wishlist
                    )
                    val groupedList = remember(state.visibleList) {
                        sectionsList.mapNotNull { cat ->
                            val list = state.visibleList.filter { it.category == cat }
                            if (list.isEmpty()) null else cat to list
                        }
                    }
                    LazyColumn(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        groupedList.forEach { (category, booksList) ->
                            stickyHeader {
                                Text(
                                    text = "${category.name} (${booksList.size})",
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.background(MaterialTheme.colorScheme.background)
                                )
                            }
                            items(
                                count = booksList.size,
                                key = { index -> booksList[index].id },
                                contentType = { _ -> "Book" }) { index ->
                                BookCard(
                                    booksList[index],
                                    vm::toggleFavoritesIcon,
                                    snackbar,
                                    scope
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookCard(
    book: Book,
    onFavoriteToggle: (String) -> Unit,
    snackbar: SnackbarHostState,
    scope: CoroutineScope
) {
    OutlinedCard {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = book.title, style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = book.authorName, style = MaterialTheme.typography.bodyMedium
                )
                // Category Pill
                CategoryPill(book.category)
            }
            IconButton(onClick = {
                onFavoriteToggle(book.id)
                scope.launch {
                    snackbar.showSnackbar(if (book.isFavorited) "Removed to favorites" else "Added from favorites")
                }
            }) {
                Icon(
                    imageVector = if (book.isFavorited) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite heart",
                    tint = if (book.isFavorited) Color(0xFFFF0000) else Color(0xFF020202)
                )
            }
        }
    }
}

@Composable
fun CategoryPill(category: BookCategory) {
    val (content, container) = when (category) {
        BookCategory.Reading -> Color(0xFF1E3BFF) to Color(0xFFB8DAFF)
        BookCategory.Completed -> Color(0xFF007215) to Color(0xFFC7FFB6)
        else -> Color(0xFF232323) to Color(0xFFDADADA)
    }
    Surface(shape = RoundedCornerShape(999.dp), color = container) {
        Text(
            text = category.name,
            color = content,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun EmptyScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(shape = CircleShape, color = MaterialTheme.colorScheme.secondaryContainer) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Empty search icon",
                modifier = Modifier
                    .size(64.dp)
                    .padding(16.dp),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        Text(
            text = "No books found", style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "Try adjusting your search terms or filters to see more results.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun FilterChips(selected: BookCategory, onSelectCategory: (BookCategory) -> Unit) {
    val cats = BookCategory.entries
    LazyRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(cats.size) { i ->
            val cat = cats[i]
            FilterChip(
                selected = selected == cat,
                onClick = { onSelectCategory(cat) },
                label = { Text(cat.name) })
        }
    }
}