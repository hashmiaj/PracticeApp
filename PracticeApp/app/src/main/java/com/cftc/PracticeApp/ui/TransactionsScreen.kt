package com.cftc.PracticeApp.ui

import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cftc.PracticeApp.model.Transaction
import com.cftc.PracticeApp.viewmodel.TransactionsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreen() {
    val vm: TransactionsViewModel = viewModel()
    val state: TransactionsUiState by vm.state.collectAsState()

    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar({
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Transactions", modifier = Modifier.weight(1f))
                    IconButton(onClick = vm::refresh) {
                        Icon(Icons.Outlined.Refresh, contentDescription = "Refresh Icon button")
                    }
                }
            })
        },
        snackbarHost = { SnackbarHost(snackbar) }
    ) { inner ->
        Column(
            modifier = Modifier.padding(inner),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Search bar
            OutlinedTextField(
                value = state.inputQuery,
                onValueChange = vm::onSearchQueryChanged,
                placeholder = { Text("Search transactions...") },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Search,
                        contentDescription = "Search bar icon"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
            )

            // List of transactions with stick headers

            when {
                state.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                state.showEmpty -> {
                    EmptyScreen()
                }
                // state.onError -> {}
                else -> {
                    val monthLabelFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
                    val groupByMonth =
                        state.visibleList.groupBy { LocalDate.parse(it.date).withDayOfMonth(1) }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        groupByMonth.forEach { (month, transactions) ->
                            val monthLabel = monthLabelFormatter.format(month)
                            stickyHeader {
                                Column(
                                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = monthLabel,
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    HorizontalDivider()
                                }
                            }
                            items(
                                count = transactions.size,
                                key = { index -> transactions[index].id },
                                contentType = { _ -> "transaction" }
                            ) { index ->
                                TransactionCard(
                                    transactions[index],
                                    vm::toggleFavoritesIcon,
                                    snackbar,
                                    scope
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Surface(shape = CircleShape, color = MaterialTheme.colorScheme.secondaryContainer) {
            Icon(
                imageVector = Icons.Filled.AttachMoney,
                contentDescription = "Empty screen money icon",
                modifier = Modifier
                    .size(64.dp)
                    .padding(16.dp)
            )
        }
        Text(
            text = "No transactions yet",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Your financial trnsactions will appear here when you add them.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

    }
}

@Composable
fun TransactionCard(
    transaction: Transaction,
    toggleFavorite: (String) -> Unit,
    snackbar: SnackbarHostState,
    scope: CoroutineScope
) {
    val isIncome = transaction.cost > 0
    val formattedCost = NumberFormat.getCurrencyInstance(Locale.US).format(transaction.cost)
    OutlinedCard(modifier = Modifier.padding(16.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = transaction.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = if (isIncome) "+$formattedCost" else formattedCost,
                    style = MaterialTheme.typography.titleLarge,
                    color = if (isIncome) Color(0xFF208500) else Color(0xFFFF1212)
                )
                IconButton(onClick = {
                    toggleFavorite(transaction.id)
                    scope.launch {
                        snackbar.showSnackbar(if (transaction.isFavorited) "Removed from favorites" else "Added to favorites")
                    }
                }) {
                    Icon(
                        imageVector = if (transaction.isFavorited) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorites icon",
                        tint = if (transaction.isFavorited) Color(0xFFFF1212) else Color(0xFF000000)
                    )
                }
            }
            Text(
                text = transaction.merchant,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}