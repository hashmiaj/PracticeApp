package com.cftc.PracticeApp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FormatLineSpacing
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.Waves
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.cftc.PracticeApp.viewmodel.ChatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    val vm: ChatViewModel = viewModel()
    val state: ChatUiState by vm.state.collectAsState()

    var modelTitle by remember { mutableStateOf("Claude Sonnet 4.5") }
    var showSheet by remember { mutableStateOf(false) }

    var messageText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back arrow icon"
                        )
                    }
                },
                title = {
                    Button(
                        onClick = { showSheet = true }, colors = ButtonColors(
                            Color.Transparent,
                            Color.Black,
                            Color.Transparent,
                            Color.Transparent
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "$modelTitle ↓")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.RemoveRedEye,
                            null
                        )
                    }
                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 32.dp)
                    .fillMaxWidth()
                    .imePadding(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = messageText,
                    onValueChange = { newValue ->
                        messageText = newValue
                    },
                    placeholder = {
                        Text(
                            text = "Chat with Claude...",
                            style = MaterialTheme.typography.labelLarge
                        )
                    },
                    textStyle = MaterialTheme.typography.labelLarge,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.AddCircle,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.FormatLineSpacing,
                            contentDescription = null
                        )
                    }
                    Button(onClick = {}) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = null,
                            )
                            Text(
                                "Research"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Outlined.Mic,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Outlined.Waves,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    ) { inner ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(inner),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.showEmpty -> {
                EmptyScreen(inner)
            }

            else -> {
            }
        }
        if (showSheet) {
            ModalBottomSheet(onDismissRequest = { showSheet = false }) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    ModelRow(
                        "Claude Opus 4.1",
                        "Powerful, large model for complex challenges",
                        modelTitle == "Claude Opus 4.1",
                        {
                            modelTitle = "Claude Opus 4.1"
                            showSheet = false
                        })
                    ModelRow(
                        "Claude Sonnet 4.5",
                        "Smartest model. Efficient for everyday use.",
                        modelTitle == "Claude Sonnet 4.5",
                        {
                            modelTitle = "Claude Sonnet 4.5"
                            showSheet = false
                        })
                }
            }
        }
    }
}

@Composable
fun EmptyScreen(inner: PaddingValues) {
    Box(
        modifier = Modifier
            .padding(inner)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = "https://pbs.twimg.com/media/Gc2r0cfW0AAKQw2.jpg",
                contentDescription = "Claude logo",
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "How can I help you this evening?",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ModelRow(
    title: String,
    description: String,
    isSelected: Boolean,
    selectModel: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(4.dp)
            .clickable(true, onClick = selectModel)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(title, style = MaterialTheme.typography.bodyMedium)
            Text(description, style = MaterialTheme.typography.labelMedium)
        }
        if (isSelected) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Check icon"
            )
        }
    }
}