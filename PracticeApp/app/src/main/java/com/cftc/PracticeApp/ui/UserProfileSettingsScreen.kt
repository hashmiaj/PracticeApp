package com.cftc.PracticeApp.ui

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.contentType
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.cftc.PracticeApp.viewmodel.UserViewModel

@Composable
fun UserProfileSettingsScreen() {
    val viewModel: UserViewModel = viewModel()
    val state: UserUiState by viewModel.state.collectAsState()

    LaunchedEffect(Unit) { viewModel.getUserDetails() }

    Box(
        Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn(
                Modifier.fillMaxSize(),
                contentPadding = PaddingValues(all = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    //Profile Pic
                    AsyncImage(
                        model = state.userData?.imageUrl,
                        contentDescription = "Profile picture of ${state.userData?.name}",
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .size(100.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                item {
                    Text(
                        text = state.userData?.name.orEmpty(),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = state.userData?.emailAddress.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }

                item {
                    Surface(
                        color = MaterialTheme.colorScheme.secondary.copy(0.1f),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Row(Modifier.padding(4.dp)) {
                            Icon(
                                imageVector = Icons.Outlined.EditNote,
                                contentDescription = "Edit profile icon",
                                modifier = Modifier
                                    .padding(end = 2.dp)
                                    .size(24.dp)
                            )
                            Text(
                                text = "Edit Profile",
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                item {
                    HorizontalDivider()
                }

                item {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        SettingsOption(
                            Icons.Outlined.Person,
                            "Account Settings",
                            "Personal information and preferences"
                        )
                        SettingsOption(
                            Icons.Outlined.Notifications,
                            "Notifications",
                            "Push notifications and email alerts"
                        )
                        SettingsOption(
                            Icons.Outlined.Shield,
                            "Privacy & Security",
                            "Password, two-factor authentication"
                        )
                        SettingsOption(
                            Icons.Outlined.CreditCard,
                            "Payment Methods",
                            "Manage cards and billing"
                        )
                        SettingsOption(
                            Icons.Outlined.Help,
                            "Help & Support",
                            "FAQs, contact us, feedback"
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun SettingsOption(
    iconImageVector: ImageVector,
    settingsTitle: String,
    settingsDescription: String
) {
    Row(
        Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = iconImageVector,
            contentDescription = "Icon for $settingsTitle",
            modifier = Modifier.size(32.dp),
            tint = Color(0xFF858B96)
        )

        Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.weight(1f)) {
            Text(text = settingsTitle, style = MaterialTheme.typography.titleMedium)
            Text(text = settingsDescription, style = MaterialTheme.typography.bodyMedium)
        }

        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = "Icon for navigating to $settingsTitle",
            tint = Color(0xFF858B96)
        )
    }
}