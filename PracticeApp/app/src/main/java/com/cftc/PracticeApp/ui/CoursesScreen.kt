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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.cftc.PracticeApp.model.Course
import com.cftc.PracticeApp.model.CourseCategory
import com.cftc.PracticeApp.uistate.CoursesUiState
import com.cftc.PracticeApp.viewmodel.CoursesViewModel

@Composable
fun CoursesScreen() {
    val vm: CoursesViewModel = viewModel()
    val state: CoursesUiState by vm.state.collectAsState()

    Scaffold { inner ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            stickyHeader {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(bottom = 12.dp)
                ) {
                    Column() {
                        SearchEntry(state.inputQuery, vm::updateSearchQuery)
                        FilterChips(state.selected, vm::updateSelectedCategory)
                    }
                }
            }

            when {
                state.isLoading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                state.showEmpty -> {
                    item {
                        EmptyList(state.inputQuery)
                    }
                }

                else -> {
                    items(
                        count = state.visibleList.size,
                        key = { index -> state.visibleList[index].id },
                        contentType = { _ -> "course" }
                    ) { index ->
                        CourseCard(state.visibleList[index])
                    }
                }
            }

        }
    }
}

@Composable
fun SearchEntry(inputQuery: String, onInputQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = inputQuery,
        onValueChange = onInputQueryChange,
        placeholder = { Text("Search courses...") },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "search icon") }
    )
}

@Composable
fun FilterChips(selected: CourseCategory, onSelectChange: (CourseCategory) -> Unit) {
    val cats = listOf(
        CourseCategory.All,
        CourseCategory.Design,
        CourseCategory.AIML,
        CourseCategory.Mobile,
        CourseCategory.Web
    )

    LazyRow(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(cats.size) { index ->
            val cat = cats[index]
            FilterChip(
                selected = selected == cat,
                onClick = { onSelectChange(cat) },
                label = { Text(cat.name) }
            )
        }
    }
}

@Composable
fun CourseCard(course: Course) {
    OutlinedCard(modifier = Modifier.padding(16.dp), shape = RoundedCornerShape(20.dp)) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CourseImage(course.imageUrl)
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = course.title,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = course.prof,
                    style = MaterialTheme.typography.bodyMedium,
                )
                RatingStar(course.rating)
            }
            Icon(
                imageVector = if (course.isFavorite) Icons.Filled.Star else Icons.Outlined.StarBorder,
                contentDescription = "favorite star",
                modifier = Modifier.size(32.dp),
                tint = if (course.isFavorite) Color(0xFFFFBE00) else Color(0xFF000000)
            )
        }
    }
}

@Composable
fun RatingStar(rating: Double) {
    val filledStars = rating.toInt().coerceIn(0, 5)
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(5) { i ->
            val shouldFill = i < filledStars
            Icon(
                imageVector = if (shouldFill) Icons.Filled.Star else Icons.Outlined.StarBorder,
                contentDescription = "rating star",
                modifier = Modifier.size(12.dp),
                tint = Color(0xFFE0C406)
            )
        }
        Text(text = rating.toString(), style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun CourseImage(imageUrl: String?) {
    if (imageUrl == null) {
        Surface(shape = RoundedCornerShape(20.dp)) {
            Icon(
                imageVector = Icons.Filled.PhotoLibrary,
                contentDescription = "image",
                modifier = Modifier.size(60.dp)
            )
        }
    } else {
        AsyncImage(
            model = imageUrl,
            contentDescription = "image of course",
            modifier = Modifier.size(60.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun EmptyList(inputQuery: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Book,
            contentDescription = "book placeholder image",
            modifier = Modifier.size(72.dp),
        )
        Text(text = "No courses found", style = MaterialTheme.typography.titleLarge)
        Text(
            text = "No courses match \"$inputQuery\".",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}