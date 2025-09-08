package com.cftc.PracticeApp.ui

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.key.Key.Companion.Tab
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cftc.PracticeApp.viewmodel.TransactionsViewModel
import com.cftc.PracticeApp.model.PaymentType
import com.cftc.PracticeApp.model.Transaction
import com.cftc.PracticeApp.model.TransactionType
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreen() {
    val vm: TransactionsViewModel = viewModel()
    val state: TransactionsUiState by vm.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar({
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "Transactions", modifier = Modifier.weight(1f))
                    IconButton(onClick = vm::getAllTransactions ) {
                        Icon(
                            imageVector = Icons.Outlined.Refresh,
                            contentDescription = "Refresh Icon"
                        )
                    }
                }
            })
        }) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Search bar
            OutlinedTextField(
                value = state.inputQuery,
                onValueChange = vm::onInputQueryValueChanged,
                placeholder = { Text("Search transactions...") },
                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = "Search icon") },
                modifier = Modifier.fillMaxWidth()
            )

            // Tabs
            PaymentTypeTabs(state.selectedPaymentType, vm::onPaymentTypeValueChange)

            // Filter Chips
            FilterChips(state.selectedTransactionType, vm::toggleTransactionType)


            when {
                state.isLoading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                state.showEmpty -> {
                    EmptyScreen(vm::onClearFilters)
                }

                else -> {
                    val groupedByDate =
                        state.visibleList.map {
                            it.copy(
                                date = LocalDate.parse(it.date).toString()
                            )
                        }
                    val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
                    val grouped = groupedByDate.groupBy {
                        LocalDate.parse(it.date).format(formatter)
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        grouped.forEach { date, transactions ->
                            stickyHeader {
                                DateHeader(date)
                            }
                            items(
                                count = transactions.size,
                                key = { index -> transactions[index].id },
                                contentType = { _ -> "transaction" }
                            ) { index ->
                                TransactionRow(transactions[index])
                            }

                        }
                    }
                }
            }
        }

    }
}

@Composable
fun EmptyScreen(onClearFilters: () -> Unit) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "Empty search icon",
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = "No transactions match your filters",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "Try adjusting your search or filters to see more transactions.",
            style = MaterialTheme.typography.bodyMedium
        )
        OutlinedButton(onClick = onClearFilters) {
            Text("Clear filters", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionRow(transaction: Transaction) {
    val date = LocalDate.parse(transaction.date)
    val formattedDate = date.format(DateTimeFormatter.ofPattern("MMM d"))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.AttachMoney,
            contentDescription = "Transaction Icon",
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
        )
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = transaction.title, style = MaterialTheme.typography.titleLarge)
            Text(
                text = "${formattedDate} · ${transaction.cardDetails}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Text(
            text = if (transaction.paymentType == PaymentType.Expense) "-$${transaction.price}" else "+$${transaction.price}"
        )
    }
}

@Composable
fun DateHeader(date: String) {
    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        HorizontalDivider()
        Text(date, style = MaterialTheme.typography.bodyLarge)
        HorizontalDivider()
    }
}

@Composable
fun FilterChips(
    selectedTransactionsList: List<TransactionType>,
    onTransactionTypeUpdated: (TransactionType) -> Unit
) {
    val cats = TransactionType.entries
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(cats.size) { i ->
            val cat = cats[i]
            val isSelected = selectedTransactionsList.contains(cat)
            FilterChip(
                selected = isSelected,
                onClick = { onTransactionTypeUpdated(cat) },
                label = { Text(cat.name) })
        }
    }
}

@Composable
fun PaymentTypeTabs(selectedPaymentType: PaymentType, onPaymentTypeClick: (PaymentType) -> Unit) {
    val tabs = PaymentType.entries
    val selectedIndex = tabs.indexOf(selectedPaymentType)
    TabRow(selectedTabIndex = selectedIndex) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = index == selectedIndex,
                onClick = { onPaymentTypeClick(tab) },
                text = { Text(tab.name) })
        }
    }
}