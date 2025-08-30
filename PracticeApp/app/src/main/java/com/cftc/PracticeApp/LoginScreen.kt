package com.cftc.PracticeApp

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen() {
    val vm: LoginViewModel = viewModel()
    val state by vm.state.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val canSubmit = email.isNotBlank() && password.isNotBlank() && !state.isLoading

    // Light gray app bg
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F4F4))
            .padding(24.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize(),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 32.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Welcome back",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    textAlign = TextAlign.Center
                )

                // Email
                Text(
                    text = "Email address",
                    style = MaterialTheme.typography.labelLarge.copy(color = Color(0xFF666666)),
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )

                // Password
                Text(
                    text = "Password",
                    style = MaterialTheme.typography.labelLarge.copy(color = Color(0xFF666666)),
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = if (passwordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    trailingIcon = {
                        val label = if (passwordVisible) "Hide" else "Show"
                        TextButton(onClick = { passwordVisible = !passwordVisible }) {
                            Text(label)
                        }
                    }
                )

                // Forgot password
                Text(
                    text = "Forgot password?",
                    color = Color(0xFF1A73E8),
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 24.dp)
                )

                // Login button
                Button(
                    onClick = { vm.loginUser(email, password) },
                    enabled = canSubmit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1241DC),
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF9DB3FF),
                        disabledContentColor = Color.White.copy(alpha = 0.7f)
                    ),
                    shape = MaterialTheme.shapes.large
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text("Log in")
                    }
                }

                Spacer(Modifier.height(24.dp))

                // "Don't have an account? Sign up" with only "Sign up" clickable & blue
                val annotated = buildAnnotatedString {
                    append("Don't have an account? ")
                    pushStringAnnotation(tag = "signup", annotation = "signup")
                    withStyle(SpanStyle(color = Color(0xFF1A73E8))) { append("Sign up") }
                    pop()
                }
                Text(
                    text = annotated,
                    style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

//My wack ass design vs GPTs^^^
//@Composable
//fun LoginScreen() {
//    val viewModel: LoginViewModel = LoginViewModel()
//    val state by viewModel.state.collectAsState()
//
//    Box(Modifier.fillMaxSize().background(color = Color(0xFFE7E1E1))) {
//        Column(
//            Modifier
//                .padding(horizontal = 32.dp)
//                .background(color = Color(0xFFFFFFFF))
//                .fillMaxSize()
//                .padding(top = 100.dp)
//        ) {
//            Text(
//                "Welcome back",
//                Modifier
//                    .padding(horizontal = 32.dp)
//                    .fillMaxWidth()
//                    .padding(bottom = 32.dp),
//                Color.Black,
//                textAlign = TextAlign.Center,
//                fontSize = 42.sp
//            )
//
//            var emailAddress by remember { mutableStateOf("") }
//            var password by remember { mutableStateOf("") }
//
//            Text(
//                "Email address",
//                Modifier
//                    .padding(horizontal = 16.dp)
//                    .fillMaxWidth()
//                    .padding(bottom = 4.dp),
//                Color.DarkGray,
//                textAlign = TextAlign.Start,
//                fontSize = 18.sp,
//            )
//
//            OutlinedTextField(
//                emailAddress,
//                { emailAddress = it },
//                Modifier
//                    .padding(horizontal = 16.dp)
//                    .fillMaxWidth()
//                    .padding(bottom = 8.dp)
//            )
//
//            Text(
//                "Password",
//                Modifier
//                    .padding(horizontal = 16.dp)
//                    .fillMaxWidth()
//                    .padding(bottom = 4.dp),
//                Color.DarkGray,
//                textAlign = TextAlign.Start,
//                fontSize = 18.sp,
//            )
//
//            OutlinedTextField(
//                password,
//                { emailAddress = it },
//                Modifier
//                    .padding(horizontal = 16.dp)
//                    .fillMaxWidth()
//                    .padding(bottom = 4.dp)
//            )
//
//            Text(
//                "Forgot password?",
//                Modifier
//                    .padding(horizontal = 16.dp)
//                    .fillMaxWidth()
//                    .padding(bottom = 8.dp),
//                Color.Blue,
//                textAlign = TextAlign.End,
//                fontSize = 12.sp,
//            )
//
//
//            Button(
//                onClick = { viewModel.loginUser(emailAddress, password) },
//                modifier = Modifier
//                    .padding(horizontal = 16.dp)
//                    .fillMaxWidth()
//                    .align(Alignment.CenterHorizontally)
//                    .height(100.dp)
//                    .padding(bottom = 32.dp),
//                colors = ButtonColors(
//                    containerColor = Color(0xFF1241DC),
//                    contentColor = Color.White,
//                    disabledContentColor = Color(0xFF5D5A5E),
//                    disabledContainerColor = Color(0xFF597BF3)
//                )
//            ) {
//                if (state.isLoading) {
//                    CircularProgressIndicator()
//                } else {
//                    Text(text = "Log in", color = Color.White)
//                }
//            }
//
//
//            Text(
//                "Don't have an account? Sign up",
//                Modifier
//                    .padding(horizontal = 16.dp)
//                    .fillMaxWidth()
//                    .padding(bottom = 16.dp),
//                Color.Black,
//                textAlign = TextAlign.End,
//                fontSize = 12.sp,
//            )
//        }
//    }
//
//}
