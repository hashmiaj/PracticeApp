package com.cftc.PracticeApp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CompareArrows
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TranslationScreen() {
    var contentText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = "Star icon",
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                },
                title = {},
                actions = { ProfileActionIcon() }
            )
        },
        bottomBar = {
            TranslationBottomBar()
        }
    ) { inner ->
        Box(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
        ) {
            TextField(
                value = contentText,
                onValueChange = { newValue ->
                    contentText = newValue
                },
                textStyle = MaterialTheme.typography.headlineMedium,
                placeholder = {
                    Text(
                        "Enter text",
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun TranslationBottomBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TranslationLanguageButtons()
        TranslationActionButtons()
    }
}

@Composable
fun TranslationActionButtons() {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.BottomCenter) {
            ActionButton(Icons.Filled.People, "Conversations")
        }
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.TopCenter) {
            RecordActionButton()
        }
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.BottomCenter) {
            ActionButton(Icons.Filled.CameraAlt, "Camera")
        }
    }
}

@Composable
fun RecordActionButton() {
    Box(
        modifier = Modifier
            .background(color = Color(0xFFFFC6AD), shape = CircleShape)
            .clickable(true, onClick = {})
            .size(120.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.Mic,
            contentDescription = "Mic action button icon",
            tint = Color.Black,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .size(48.dp)
        )
    }
}

@Composable
fun ActionButton(iconType: ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .background(color = Color(0xFF704F3E), shape = CircleShape)
                .clickable(true, onClick = {})
                .size(32.dp)
        ) {
            Icon(
                imageVector = iconType,
                contentDescription = "$label icon",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
                    .size(24.dp)
            )
        }

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun TranslationLanguageButtons() {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Button(
            onClick = {}, modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "English", style = MaterialTheme.typography.bodyMedium)
        }
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.CompareArrows,
                contentDescription = "Switch language icon"
            )
        }
        Button(
            onClick = {}, modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Spanish", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun ProfileActionIcon() {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .background(color = Color(0xFF41A11A), shape = CircleShape)
            .clickable(true, onClick = {})
            .size(32.dp)
    ) {
        Text(
            text = "a",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.align(Alignment.Center),
            color = Color.White
        )
    }
}