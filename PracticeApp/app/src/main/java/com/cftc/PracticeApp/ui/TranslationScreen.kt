package com.cftc.PracticeApp.ui

import android.view.Surface
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TranslationScreen() {

    val translationText = remember { mutableStateOf("Enter text") }

    Scaffold(
        topBar = { TopAppBar({ TranslateTopBar() }) },
        bottomBar = { TranslateFunctions() },
        containerColor = Color(0x19120c),
    ) { inner ->
        Column(modifier = Modifier.padding(inner)) {
            // text field
            Text(
                text = translationText.value,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )


            /*
            * TextField(
                value = translationText.value,
                onValueChange = { translationText.value },
                modifier = Modifier.padding(16.dp)
            )
            * */

        }
    }
}

@Composable
fun TranslateTopBar() {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Star Icon
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Favorites star top bar",
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(Modifier.weight(1f))
        // profile icon (either an icon or a async image)
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "User icon for top row",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun TranslateFunctions() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {}, modifier = Modifier.weight(1f)) {
                Text(
                    text = "English",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }

            Icon(
                imageVector = Icons.Outlined.ArrowUpward,
                contentDescription = "Translate swap icon",
                modifier = Modifier.size(24.dp)
            )

            Button(onClick = {}, modifier = Modifier.weight(1f)) {
                Text(
                    text = "Spanish",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                ConversationOrCameraIcon(Icons.Filled.People, "Conversations icon", "Conversations")
            }
            Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Button(
                    onClick = {},
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .size(200.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Mic,
                        contentDescription = "Mic Icon",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(16.dp)
                    )
                }
            }
            Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
            ConversationOrCameraIcon(Icons.Filled.Camera, "Camera icon", "Camera")
                }
        }
    }
}

@Composable
fun ConversationOrCameraIcon(
    imageVector: ImageVector, contentDescription: String, labelText: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Button(
            onClick = {}, shape = CircleShape, modifier = Modifier.size(64.dp)
        ) {
            Icon(imageVector = imageVector, contentDescription = contentDescription)
        }
        Text(
            text = labelText,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }

}