package com.cftc.PracticeApp.ui

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Usb
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.cftc.PracticeApp.model.ChatThread
import com.cftc.PracticeApp.viewmodel.ChatsViewModel
import org.jetbrains.annotations.Async
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreen() {
    val vm: ChatsViewModel = viewModel()
    val state: ChatsUiState by vm.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar({
                ChatsTopBar()
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
                onValueChange = vm::onUpdateSearchQuery,
                placeholder = { Text("Ask Meta AI or Search") },
                leadingIcon = { Icon(Icons.Outlined.Search, "search icon") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )

            // Filters Chips
            FilterChips(state.selectedCategory, onFilterSelected = vm::onCategorySelected)

            // List of Messages or loading or empty
            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                state.showEmptyList -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No results found")
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(
                            count = state.visibleList.size,
                            key = { index -> state.visibleList[index].id }
                        ) { index ->
                            MessageRow(state.visibleList[index])
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessageRow(chat: ChatThread) {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val dateTime = LocalDateTime.parse(chat.previewMessageTime, inputFormatter)
    val outputFormatter = DateTimeFormatter.ofPattern("h:mm a")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfilePicture(chat.profilePhoto)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = chat.authorName,
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = chat.previewMessage,
                style = MaterialTheme.typography.bodySmall,
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = dateTime.format(outputFormatter),
                style = MaterialTheme.typography.labelSmall,
            )
            if (chat.isPinned) {
                Icon(
                    Icons.Filled.PushPin,
                    contentDescription = "push pin icon"
                )
            }
        }

    }
}

@Composable
fun ProfilePicture(imageUrl: String?) {
    if (imageUrl == null) {
        Surface(shape = CircleShape, modifier = Modifier.size(44.dp), color = Color(0xFF7A7A7A)) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Profile pic icon",
                modifier = Modifier.padding(8.dp)
            )
        }
    } else {
        Surface(shape = CircleShape, modifier = Modifier.size(44.dp)) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Profile pic image",
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun FilterChips(selected: ChatsCategory, onFilterSelected: (ChatsCategory) -> Unit) {
    val cats = ChatsCategory.entries
    LazyRow(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(count = cats.size) { index ->
            val cat = cats[index]
            FilterChip(
                selected = selected == cat,
                onClick = { onFilterSelected(cat) },
                label = { Text(cat.name) }
            )
        }
    }
}

@Composable
fun ChatsTopBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "WhatsApp",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.weight(1f)
        )

        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Outlined.CameraAlt,
                contentDescription = "Camera icon button"
            )
        }

        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Outlined.MoreVert,
                contentDescription = "More options icon button"
            )
        }
    }
}