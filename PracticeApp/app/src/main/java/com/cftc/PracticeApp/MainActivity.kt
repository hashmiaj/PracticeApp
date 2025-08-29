package com.cftc.PracticeApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cftc.PracticeApp.ui.BlogViewModel
import com.cftc.PracticeApp.ui.BlogsScreen
import com.cftc.PracticeApp.ui.theme.PracticeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticeAppTheme { AppNavHost() }
        }
    }
}

@Composable
fun AppNavHost() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "main") {

        composable("main") {
            MainScreen(onNavigate = { nav.navigate("blogs") })
        }

        composable("blogs") {
            // obtain VM in the destination, then pass it in
            val vm: BlogViewModel = viewModel()
            BlogsScreen(vm)
        }
    }
}

@Composable
fun MainScreen(onNavigate: () -> Unit) {
    Scaffold { padding ->
        Button(
            onClick = onNavigate,
            modifier = Modifier.padding(padding)
        ) {
            Text("Go to Blogs")
        }
    }
}