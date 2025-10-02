package com.cftc.PracticeApp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cftc.PracticeApp.model.ResearchPaper
import com.cftc.PracticeApp.viewmodel.ResearchPapersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResearchPapersScreen() {
    val vm: ResearchPapersViewModel = viewModel()
    val state: ResearchPapersUiState by vm.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "AI Research Papers",
                    style = MaterialTheme.typography.titleLarge
                )
            })
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
        ) {
            // Search Bar
            OutlinedTextField(
                value = state.inputQuery,
                onValueChange = vm::updateSearchQuery,
                placeholder = { Text("Search papers...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search bar icon"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            // List of items

            when {
                state.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                state.showEmpty -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "No papers found", style = MaterialTheme.typography.labelSmall)
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            count = state.visibleList.size,
                            key = { index -> state.visibleList[index].id },
                            contentType = { _ -> "Research Paper" }) { index ->
                            ResearchPaperCard(state.visibleList[index], vm::toggleBookmark)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ResearchPaperCard(paper: ResearchPaper, toggleBookmark: (String) -> Unit) {
    Box(modifier = Modifier.padding(16.dp)) {
        OutlinedCard {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = paper.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "${paper.author} · ${paper.year}",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                IconButton(onClick = { toggleBookmark(paper.id) }) {
                    Icon(
                        imageVector = if (paper.isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = "Bookmark"
                    )
                }
            }
        }
    }

}