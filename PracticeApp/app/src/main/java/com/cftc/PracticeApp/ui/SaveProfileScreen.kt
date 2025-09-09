package com.cftc.PracticeApp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cftc.PracticeApp.model.User
import com.cftc.PracticeApp.viewmodel.SaveProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveProfileScreen() {
    val vm: SaveProfileViewModel = viewModel()
    val state: SaveProfileUiState by vm.state.collectAsState()

    // User entries
    val name = rememberSaveable { mutableStateOf("") }
    val email = rememberSaveable { mutableStateOf("") }
    val phone = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordConfirm = rememberSaveable { mutableStateOf("") }

    // Has the user touched the text field
    val nameTouched = rememberSaveable { mutableStateOf(false) }
    val emailTouched = rememberSaveable { mutableStateOf(false) }
    val passwordTouched = rememberSaveable { mutableStateOf(false) }
    val passwordConfirmTouched = rememberSaveable { mutableStateOf(false) }

    // Check for each if its valid
    val nameValid = name.value.length > 1
    val emailValid = remember(email) {
        android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
    }
    val phoneValid = phone.value.isEmpty() || phone.value.all { it.isDigit() }
    val passwordValid = password.value.length > 7
    val passwordConfirmValid = password.value == passwordConfirm.value

    //Visible password
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordConfirmVisible by remember { mutableStateOf(false) }


    //Error messages
    val nameError = if (name.value.trim().isEmpty()) "Name is required"
    else if (!nameValid) "Name must be at least 2 characters" else ""
    val emailError = if (email.value.trim().isEmpty()) "Email is required"
    else if (!emailValid) "Please enter a valid email address" else ""
    val phoneError = if (!phoneValid) "Phone number must contain only numbers" else ""
    val passwordError = if (password.value.trim()
            .isEmpty()
    ) "Password is required" else if (!passwordValid) "Password must be at least 8 characters." else ""
    val passwordConfirmError = if (passwordConfirm.value.trim()
            .isEmpty()
    ) "Please confirm your password" else if (!passwordConfirmValid) "Passwords do not match" else ""

    val isValidInput =
        nameError.isBlank() && emailError.isBlank() && phoneError.isBlank() && passwordError.isBlank() && passwordConfirmError.isBlank()

    val user = User(
        "1", name.value, email.value, phone.value, password.value
    )

    Scaffold(topBar = { TopAppBar({ Text("Edit Profile") }) }, bottomBar = {
        SaveProfileButton(
            isValidInput, vm::saveProfile, user, state.isSavingProfile
        )
    }) { inner ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { HorizontalDivider() }
            item {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Name *", style = MaterialTheme.typography.titleMedium
                    )
                    OutlinedTextField(
                        value = name.value,
                        onValueChange = {
                            name.value = it
                            nameTouched.value = true
                        },
                        placeholder = { Text("Enter your full name") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        isError = !nameError.isBlank() && nameTouched.value,
                        supportingText = {
                            if (!nameError.isBlank() && nameTouched.value) Text(
                                nameError
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            item {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Email *", style = MaterialTheme.typography.titleMedium
                    )
                    OutlinedTextField(
                        value = email.value,
                        onValueChange = {
                            email.value = it
                            emailTouched.value = true
                        },
                        placeholder = { Text("Enter your email address") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        isError = !emailError.isBlank() && emailTouched.value,
                        supportingText = {
                            if (!emailError.isBlank() && emailTouched.value) Text(
                                emailError
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            item {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Phone Number", style = MaterialTheme.typography.titleMedium
                    )
                    OutlinedTextField(
                        value = phone.value,
                        onValueChange = { phone.value = it },
                        placeholder = { Text("Enter your phone number (optional)") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        isError = !phoneError.isBlank(),
                        supportingText = { if (!phoneError.isBlank()) Text(phoneError) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            item {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Password *", style = MaterialTheme.typography.titleMedium
                    )
                    OutlinedTextField(
                        value = password.value,
                        onValueChange = {
                            password.value = it
                            passwordTouched.value = true
                        },
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        trailingIcon = {
                            val label = if (passwordVisible) "Hide" else "Show"
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                                    contentDescription = "visible icon"
                                )
                            }
                        },
                        placeholder = { Text("Enter your password") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        isError = !passwordError.isBlank() && passwordTouched.value,
                        supportingText = {
                            if (!passwordError.isBlank() && passwordTouched.value) Text(
                                passwordError
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            item {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Confirm Password *", style = MaterialTheme.typography.titleMedium
                    )
                    OutlinedTextField(
                        value = passwordConfirm.value,
                        onValueChange = {
                            passwordConfirm.value = it
                            passwordConfirmTouched.value = true
                        },
                        visualTransformation = if (passwordConfirmVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        trailingIcon = {
                            val label = if (passwordConfirmVisible) "Hide" else "Show"
                            IconButton(onClick = { passwordConfirmVisible = !passwordConfirmVisible }) {
                                Icon(
                                    imageVector = if (passwordConfirmVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                                    contentDescription = "visible icon"
                                )
                            }
                        },
                        placeholder = { Text("Confirm your password") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        isError = !passwordConfirmError.isBlank() && passwordConfirmTouched.value,
                        supportingText = {
                            if (!passwordConfirmError.isBlank() && passwordConfirmTouched.value) Text(
                                passwordConfirmError
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}


@Composable
fun SaveProfileButton(
    isEnabled: Boolean, onSaveProfile: (User) -> Unit, user: User, isLoading: Boolean
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onClick = { onSaveProfile(user) },
        enabled = isEnabled
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Text("Save Profile", textAlign = TextAlign.Center)
        }
    }
}