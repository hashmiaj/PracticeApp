package com.cftc.PracticeApp.ui

import android.graphics.Paint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.intellij.lang.annotations.JdkConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var showSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TopBar()
                },
            )
        }
    )
    { inner ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            AsyncImage(
                model = "https://thumbs.dreamstime.com/b/chipotle-plain-white-background-product-photography-chipotle-plain-white-background-isolated-product-photography-studio-286948001.jpg",
                contentDescription = "Background Image",
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter),
                contentScale = ContentScale.FillHeight
            )

            // Title + Promotion
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
                    .align(Alignment.TopCenter),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "LIMITED TIME",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = Color(0xFFB92B2B)
                )
                Text(
                    text = "CARNE ASADA IS BACK",
                    maxLines = 2,
                    style = MaterialTheme.typography.displayMedium,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF4D270F)
                )
            }

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                item {
                    AdCard()
                }
                item {
                    RewardsCard()
                }
            }
        }
    }
}

@Composable
fun AdCard() {
    OutlinedCard(
        modifier = Modifier
            .widthIn(min = 200.dp, max = 320.dp)
            .fillMaxHeight(0.4f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = "https://www.galonamission.com/wp-content/uploads/2021/03/Chipotle-Tomato-Salsa-12-1.jpg",
                contentDescription = "Ad Image",
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "YOUR NEXT OBSESSION",
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                color = Color(0xFFB92B2B)
            )
            Text(
                text = "ALL-NEW",
                maxLines = 2,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                color = Color(0xFF4D270F)
            )
            Text(
                text = "RED CHIMICHURRI",
                maxLines = 2,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                color = Color(0xFF4D270F)
            )
        }
    }
}

@Composable
fun RewardsCard() {
    OutlinedCard(
        modifier = Modifier
            .widthIn(min = 200.dp, max = 320.dp)
            .fillMaxHeight(0.4f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = "https://www.hatchwise.com/wp-content/uploads/2024/02/image-16.png",
                contentDescription = "Rewards Image",
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.FillWidth
            )
            Text(
                text = "CHIPOTLE REWARDS",
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                color = Color(0xFFB92B2B)
            )
            Text(
                text = "YOU'VE GOT 2,294 POINTS",
                maxLines = 2,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                color = Color(0xFF4D270F)
            )
        }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = "User icon in nav",
                modifier = Modifier
                    .size(24.dp)
            )
        }
        Box(modifier = Modifier.weight(1f)) {
            OutlinedButton(
                onClick = {},
                shape = RoundedCornerShape(999.dp),
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(text = "PICKUP OR DELIVERY")
            }
        }
        Box {
            Icon(
                imageVector = Icons.Outlined.ShoppingBag,
                contentDescription = "Shopping icon in nav",
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
}