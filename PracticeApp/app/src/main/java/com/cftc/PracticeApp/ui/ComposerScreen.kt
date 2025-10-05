package com.cftc.PracticeApp.ui

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.GifBox
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposerScreen() {
    var contentText by remember { mutableStateOf("") }
    var showDraftsAction by remember { mutableStateOf(true) }
    var enablePostButton by remember { mutableStateOf(false) }
    val twitterBlueColor = Color(0xFF0086FF)

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopAppBar(title = {}, navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.Cancel,
                        contentDescription = "cancel icon",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }, actions = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (showDraftsAction) {
                        Text(
                            text = "Drafts",
                            style = MaterialTheme.typography.titleMedium,
                            color = twitterBlueColor,
                            modifier = Modifier.clickable(true, onClick = {})
                        )
                    }
                    Button(
                        onClick = {}, enabled = enablePostButton, colors = ButtonColors(
                            containerColor = twitterBlueColor,
                            contentColor = Color(0xFFFFFFFF),
                            disabledContentColor = Color(0xFFD2D2D2),
                            disabledContainerColor = Color(0xFF4CA6FF),
                        )
                    ) {
                        Text(
                            text = "Post",
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }
            })
        },
        bottomBar = {
            BottomBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .imePadding()
            )
        }
    ) { inner ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner), contentAlignment = Alignment.TopCenter
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .size(40.dp)
                        .background(color = Color(0xFF1565C0), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "A",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                TextField(
                    value = contentText,
                    onValueChange = { newValue ->
                        contentText = newValue
                        showDraftsAction = contentText.isBlank()
                        enablePostButton = !contentText.isBlank()
                    },
                    textStyle = TextStyle(
                        fontSize = 30.sp, lineHeight = 36.sp
                    ),
                    placeholder = {
                        Text(
                            "What's happening?",
                            fontSize = 30.sp,
                            lineHeight = 36.sp
                        )
                    },
                    maxLines = Int.MAX_VALUE,
                    minLines = 3,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                )
            }
        }
    }
}

@Composable
fun BottomBar(modifier: Modifier = Modifier) {
    val darkBlueColor = Color(0xFF0F36FF)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HorizontalDivider()
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Map,
                contentDescription = "Map icon",
                tint = darkBlueColor
            )
            Text(
                text = "Everyone can reply",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        HorizontalDivider()


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Photo,
                contentDescription = "Upload photo icon",
                tint = darkBlueColor
            )
            Icon(
                imageVector = Icons.Filled.GifBox,
                contentDescription = "Upload photo icon",
                tint = darkBlueColor
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.FormatListBulleted,
                contentDescription = "Upload photo icon",
                tint = darkBlueColor
            )
            Icon(
                imageVector = Icons.Filled.PinDrop,
                contentDescription = "Upload photo icon",
                tint = darkBlueColor
            )
            Spacer(Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.AddCircle,
                contentDescription = "Upload photo icon",
                tint = darkBlueColor
            )

        }
    }
}