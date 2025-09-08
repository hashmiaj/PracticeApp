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
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Flight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cftc.PracticeApp.model.Flight
import com.cftc.PracticeApp.model.FlightStatus
import com.cftc.PracticeApp.viewmodel.ArrivalsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArrivalsScreen() {
    val vm: ArrivalsViewModel = viewModel()
    val state: ArrivalsUiState by vm.state.collectAsState()

    Scaffold(
        topBar = { TopAppBar({ Text("Arrivals") }) }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(inner)
        ) {
            // Search bar
            OutlinedTextField(
                value = state.inputQuery,
                onValueChange = vm::onSearchQueryChanged,
                label = { Text("Search airline, flight, city") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search icon") },
                modifier = Modifier.fillMaxWidth()
            )

            // Filter chips
            FilterChips(state.selected, vm::onFlightStatusSelected)

            // Flight List
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
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(
                            count = state.visibleList.size,
                            key = { index -> state.visibleList[index].id },
                            contentType = { _ -> "flights" }) { index ->
                            FlightCard(state.visibleList[index])
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun FlightCard(flight: Flight) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "${flight.airline} ${flight.flightNumber}",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "${flight.city} (${flight.cityCode})",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = flight.time,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            StatusPill(flight.status)
            Icon(
                imageVector = if (flight.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "fav heart",
                tint = if (flight.isFavorite) Color(0xFFFF0000) else Color(0xFF0C0000)
            )
        }

    }
}

@Composable
fun StatusPill(status: FlightStatus) {
    val (container, onContainer) = when (status) {
        FlightStatus.OnTime -> Color(0xFF90FF9A) to Color(0xFF0A7214)
        FlightStatus.Delayed -> Color(0xFFFF9292) to Color(0xFF9D2D2D)
        else -> Color(0xFFE1E1E1) to Color(0xFF676666)
    }
    Surface(
        shape = RoundedCornerShape(999.dp),
        color = container,              // pill background
        contentColor = onContainer      // default text/icon color inside
    ) {
        Text(
            text = status.name,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun EmptyScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 32.dp, horizontal = 4.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(color = Color(0xFFA6B9FF), shape = CircleShape) {
            Icon(
                imageVector = Icons.Outlined.Flight,
                contentDescription = "Emtpy flight icon",
                modifier = Modifier
                    .size(64.dp)
                    .padding(16.dp),
                tint = Color(0xFF1443FF)
            )
        }
        Text(
            text = "No arrivals match your filters",
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = "Try adjusting your search itsm or filters to see more results.",
            style = MaterialTheme.typography.titleMedium
        )

    }
}

@Composable
fun FilterChips(selected: FlightStatus, onFlightStatusSelected: (FlightStatus) -> Unit) {
    val cats = FlightStatus.entries
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(cats.size) { index ->
            val cat = cats[index]
            FilterChip(
                selected = selected == cat,
                onClick = { onFlightStatusSelected(cat) },
                label = { Text(cat.name) }
            )

        }
    }

}