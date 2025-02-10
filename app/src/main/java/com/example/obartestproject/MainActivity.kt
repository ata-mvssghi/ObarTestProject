package com.example.obartestproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.obartestproject.ui.screen.SecondScreen
import com.example.obartestproject.ui.screen.UserRegisterFormScreen
import com.example.obartestproject.ui.theme.ObarTestProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl ) {
                ObarTestProjectTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        val navController = rememberNavController()
                        NavHost(navController = navController, startDestination = "UserRegisterFormScreen") {
                            composable("UserRegisterFormScreen") {
                                UserRegisterFormScreen(modifier = Modifier.padding(innerPadding),navController = navController)
                            }
                            composable("SecondScreen") {
                                   SecondScreen(modifier = Modifier.padding(innerPadding), navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}
