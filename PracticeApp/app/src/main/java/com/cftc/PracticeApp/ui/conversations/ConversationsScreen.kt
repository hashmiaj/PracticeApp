package com.cftc.PracticeApp.ui.conversations

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.max

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationsRoute(vm: ConversationsViewModel = viewModel()) {
    val state by vm.state.collectAsState()
    val haptics = LocalHapticFeedback.current

    // Bottom sheet for actions
    if (state.sheetForId != null) {
        ModalBottomSheet(
            onDismissRequest = vm::closeSheet,
            sheetState = rememberModalBottomSheetState()
        ) {
            val id = state.sheetForId!!
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Conversation actions", style = MaterialTheme.typography.titleMedium)
                OutlinedButton(onClick = { vm.markRead(id) }) { Text("Mark as read") }
                OutlinedButton(onClick = { vm.togglePin(id) }) { Text("Pin / Unpin") }
                Button(
                    onClick = { vm.delete(id) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete")
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Messages") })
        }
    ) { inner ->
        Column(
            Modifier
                .padding(inner)
                .fillMaxSize()
        ) {
            // Search
            OutlinedTextField(
                value = state.query,
                onValueChange = vm::onQueryChange,
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        null
                    )
                }, // placeholder icon to avoid web fetch
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                placeholder = { Text("Search") },
                singleLine = true
            )

            when {
                state.isLoading -> Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }

                state.showEmpty -> Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { Text("No conversations") }

                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(state.filtered, key = { it.id }) { convo ->
                        ConversationRow(
                            name = convo.name,
                            lastMessage = convo.lastMessage,
                            timestamp = relativeTime(convo.timestampEpochMillis),
                            unread = convo.unreadCount,
                            initials = convo.avatarInitials,
                            pinned = convo.isPinned,
                            onClick = { /* navigate to detail (out of scope) */ },
                            onMore = {
                                vm.openSheet(convo.id)
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ConversationRow(
    name: String,
    lastMessage: String,
    timestamp: String,
    unread: Int,
    initials: String,
    pinned: Boolean,
    onClick: () -> Unit,
    onMore: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = if (pinned) 2.dp else 0.dp,
        shape = RoundedCornerShape(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .combinedClickable(
                    onClick = {},
                    onLongClick = onMore,
                    hapticFeedbackEnabled = true,
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) { Text(initials, fontWeight = FontWeight.SemiBold) }

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Text(timestamp, style = MaterialTheme.typography.labelSmall)
                }
                Spacer(Modifier.height(2.dp))
                Text(
                    text = lastMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(horizontalAlignment = Alignment.End) {
                IconButton(onClick = onMore) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More")
                }
                if (unread > 0) {
                    AssistChip(onClick = onClick, label = { Text("${minTwo(unread)}") })
                }
            }
        }
    }
}

private fun minTwo(n: Int) = max(1, n) // tiny helper for demo badge
private fun relativeTime(epoch: Long): String {
    val mins = ((System.currentTimeMillis() - epoch) / 60_000L).toInt()
    return when {
        mins < 1 -> "now"
        mins < 60 -> "${mins}m"
        mins < 60 * 24 -> "${mins / 60}h"
        else -> "${mins / (60 * 24)}d"
    }
}
