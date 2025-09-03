package com.cftc.PracticeApp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.cftc.PracticeApp.model.Dish
import com.cftc.PracticeApp.model.DishCategory
import com.cftc.PracticeApp.uistate.DishesUiState
import com.cftc.PracticeApp.viewmodel.DishesViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishesScreen() {
    val vm: DishesViewModel = viewModel()
    val state: DishesUiState by vm.state.collectAsState()

    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var itemCount = remember { mutableIntStateOf(0) }
    var totalCost = remember { mutableDoubleStateOf(0.0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("QuickBite") },
                actions = { BadgedShoppingCart(itemCount.intValue) })
        },
        snackbarHost = { SnackbarHost(snackbar) },
        bottomBar = {
            if (itemCount.intValue > 0) {
                CheckoutRow(itemCount.intValue, totalCost.doubleValue)
            }
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxWidth()
        ) {

            HorizontalDivider()

            MenuTabs(state.selectedCategory, vm::selectTab)

            when {
                state.isLoading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.visibleList.size) { index ->
                            val dish = state.visibleList[index]

                            DishCard(
                                dish = dish, onDishAddedToCart = {
                                    itemCount.intValue++
                                    totalCost.doubleValue += dish.price
                                    scope.launch {
                                        snackbar.showSnackbar("${dish.title} added to cart.")
                                    }
                                })
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun BadgedShoppingCart(itemCount: Int) {
    IconButton(onClick = {}, modifier = Modifier.padding(16.dp)) {
        BadgedBox(
            badge = {
                if (itemCount > 0) {
                    Badge {
                        Text(text = if (itemCount > 99) "99+" else itemCount.toString())
                    }
                }
            }) {
            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = "Dishes screens shopping cart",
            )
        }
    }
}

@Composable
fun MenuTabs(selected: DishCategory, onSelectTab: (DishCategory) -> Unit) {
    val tabs = DishCategory.entries
    val selectedIndex = tabs.indexOf(selected)

    TabRow(selectedTabIndex = selectedIndex) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = index == selectedIndex,
                onClick = { onSelectTab(tab) },
                text = { Text(tab.name) })
        }
    }
}

@Composable
fun DishCard(dish: Dish, onDishAddedToCart: () -> Unit) {
    OutlinedCard(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DishImage(dish.imageUrl)

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = dish.title,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = dish.description,
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = "$${dish.price}",
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            IconButton(onClick = onDishAddedToCart) {
                Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "Add to cart icon")
            }
        }
    }
}

@Composable
fun DishImage(imageUrl: String?) {
    if (imageUrl == null) {
        Icon(
            imageVector = Icons.Filled.Fastfood,
            contentDescription = "Food image icon placeholder",
            modifier = Modifier.size(32.dp)
        )
    } else {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Food image",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun CheckoutRow(itemCount: Int, totalPrice: Double) {
    val itemOrItems = if (itemCount > 1) "items" else "item"
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp)
    ) {
        HorizontalDivider()
        Row(
            Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "$itemCount $itemOrItems - $${String.format("%.2f", totalPrice)}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Button(onClick = {}) {
                Text("Checkout", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}