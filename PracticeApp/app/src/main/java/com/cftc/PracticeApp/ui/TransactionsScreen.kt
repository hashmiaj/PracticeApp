package com.cftc.PracticeApp.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cftc.PracticeApp.model.Transaction
import com.cftc.PracticeApp.model.TransactionType
import com.cftc.PracticeApp.viewmodel.TransactionsViewModel
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreen() {
    val vm: TransactionsViewModel = viewModel()
    val state: TransactionsUiState by vm.state.collectAsState()


    Scaffold(
        topBar = { TopAppBar({ Text("Transactions") }) }) { inner ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(inner),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Search bar
            OutlinedTextField(
                value = state.inputQuery,
                onValueChange = vm::onInputQueryChanged,
                placeholder = { Text("Search merchant or Category") },
                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = "Search icon") },
                modifier = Modifier.fillMaxWidth()
            )

            // Filters
            FilterChips(state.selectedType, vm::onSelectType)

            // List
            when {
                state.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                state.showEmpty -> {
                    EmptyScreen(resetFilters = vm::resetFilters)
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(
                            count = state.visibleList.size,
                            key = { index -> state.visibleList[index].id },
                            contentType = { _ -> "transaction" }
                        ) { i ->
                            TransactionCard(state.visibleList[i])
                        }
                    }
                }
            }

        }
    }
}


@SuppressLint("NewApi")
@Composable
fun TransactionCard(transaction: Transaction) {
    OutlinedCard {
        Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = transaction.title,
                    style = MaterialTheme.typography.titleLarge
                )
                StatusPill(transaction.type)
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val date = LocalDate.parse(transaction.date)
                val formattedDate = date.format(DateTimeFormatter.ofPattern("MMM d"))
                val price = NumberFormat.getCurrencyInstance(Locale.US)
                val formattedPrice = price.format(transaction.price)
                Text(
                    text = formattedPrice,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFFFF0000)
                )
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Icon(
                imageVector = if (transaction.isFavorited) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorites Icon",
                tint = if (transaction.isFavorited) Color(0xFFFF0000) else Color(0xFF000000)
            )
        }
    }
}

@Composable
fun StatusPill(type: TransactionType) {
    val (content, container) = when (type) {
        TransactionType.Food -> Color(0xFF148002) to Color(0xFFCDFFC3)
        TransactionType.Travel -> Color(0xFF0F51FF) to Color(0xFFC7D8FD)
        TransactionType.Shopping -> Color(0xFF850FFF) to Color(0xFFE6CFFF)
        else -> Color(0xFF4B4B4B) to Color(0xFFDCDCDC)
    }
    Surface(color = container, shape = RoundedCornerShape(999.dp)) {
        Text(
            text = type.name,
            color = content,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun EmptyScreen(resetFilters: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(color = Color(0xFFB1FFA4), shape = CircleShape) {
            Icon(
                imageVector = Icons.Filled.AttachMoney,
                contentDescription = "Empty screen icon",
                modifier = Modifier
                    .size(64.dp)
                    .padding(16.dp),
                tint = Color(0xFF58FF63)
            )
        }
        Text(
            text = "No transactions found",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "Try adjusting your search terms or filters to see more results.",
            style = MaterialTheme.typography.titleMedium
        )
        OutlinedButton(onClick = resetFilters) {
            Text("Clear filters")
        }
    }
}

@Composable
fun FilterChips(selected: TransactionType, onTransactionTypeSelected: (TransactionType) -> Unit) {
    val categories = TransactionType.entries
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories.size) { i ->
            val category = categories[i]
            FilterChip(
                selected = selected == category,
                onClick = { onTransactionTypeSelected(category) },
                label = { Text(category.name) }
            )
        }
    }
}