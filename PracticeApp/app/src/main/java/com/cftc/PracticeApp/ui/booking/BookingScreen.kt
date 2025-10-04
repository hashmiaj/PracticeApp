package com.cftc.PracticeApp.ui.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cftc.PracticeApp.data.PlaceSuggestion
import com.cftc.PracticeApp.ui.booking.Field

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingRoute(vm: BookingViewModel = viewModel()) {
    val state by vm.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book a ride") },
                actions = { IconButton(onClick = { /* no-op */ }) { Icon(Icons.Default.HelpOutline, contentDescription = "Help") } }
            )
        },
        bottomBar = {
            BottomBar(
                estFare = state.estFare,
                estEta = state.estEta,
                enabled = state.canConfirm,
                onConfirm = { /* TODO: navigate */ }
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(12.dp))

            // Pickup
            OutlinedTextField(
                value = state.pickupText,
                onValueChange = vm::onPickupChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .onFocusChanged {
                        if (it.isFocused) {
                            vm.onFocus(Field.PICKUP)
                        }
                    },
                placeholder = { Text("Enter pickup location") },
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.MyLocation, contentDescription = "Pickup") },
                trailingIcon = {
                    if (state.pickupText.isNotEmpty()) TextButton(onClick = { vm.clearField(Field.PICKUP) }) { Text("Clear") }
                }
            )

            // Swap control
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                OutlinedIconButton(onClick = vm::swapLocations) {
                    Icon(Icons.Default.SwapVert, contentDescription = "Swap")
                }
            }

            // Dropoff
            OutlinedTextField(
                value = state.dropoffText,
                onValueChange = vm::onDropoffChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp)
                    .onFocusChanged {
                        if (it.isFocused) {
                            vm.onFocus(Field.DROPOFF)
                        }
                    },
                placeholder = { Text("Where to?") },
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.MyLocation, contentDescription = "Dropoff") },
                trailingIcon = {
                    if (state.dropoffText.isNotEmpty()) TextButton(onClick = { vm.clearField(Field.DROPOFF) }) { Text("Clear") }
                }
            )

            TextButton(
                onClick = { /* TODO: request current location */ },
                modifier = Modifier.align(Alignment.Start)
            ) {
                Icon(Icons.Default.MyLocation, contentDescription = null)
                Spacer(Modifier.width(6.dp))
                Text("Use current location")
            }

            Spacer(Modifier.height(8.dp))

            // Suggestion states
            when {
                state.isLoading -> {
                    SuggestionsLoading()
                }
                state.error != null -> {
                    ErrorCard(message = state.error!!, onRetry = vm::retry)
                }
                state.showEmptySuggestions -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No places found")
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(state.suggestions, key = { it.id }) { s ->
                            SuggestionRow(s, onClick = { vm.selectSuggestion(s) })
                        }
                        item { Spacer(Modifier.height(80.dp)) } // keep bottom bar clear
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomBar(estFare: String?, estEta: String?, enabled: Boolean, onConfirm: () -> Unit) {
    Surface(tonalElevation = 4.dp) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text("Est. fare", style = MaterialTheme.typography.labelSmall)
                Text(estFare ?: "—", style = MaterialTheme.typography.titleMedium)
                if (estEta != null) Text(estEta, style = MaterialTheme.typography.labelSmall)
            }
            Button(onClick = onConfirm, enabled = enabled) { Text("Confirm Ride") }
        }
    }
}

@Composable
private fun SuggestionsLoading() {
    Column(Modifier.fillMaxWidth()) {
        repeat(5) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(vertical = 4.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            )
        }
    }
}

@Composable
private fun ErrorCard(message: String, onRetry: () -> Unit) {
    ElevatedCard(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text("Can't load suggestions", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(message, style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(8.dp))
            OutlinedButton(onClick = onRetry) { Text("Retry") }
        }
    }
}

@Composable
private fun SuggestionRow(s: PlaceSuggestion, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 4.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            val glyph = when (s.type) {
                PlaceSuggestion.Type.SAVED -> "★"
                PlaceSuggestion.Type.RECENT -> "⏱"
                PlaceSuggestion.Type.NEARBY -> "📍"
                PlaceSuggestion.Type.GENERAL -> "•"
            }
            Text(glyph)
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(s.primary, style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(s.secondary, style = MaterialTheme.typography.bodySmall, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}