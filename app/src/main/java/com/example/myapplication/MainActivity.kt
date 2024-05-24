package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "screen1"
                ) {
                    composable("screen1") { entry ->
//                        val text = entry.savedStateHandle.get<String>("my_text")
                        Greeting(navController)
                    }
                    composable("login") {
                        Login(navController)
                    }
                    composable("registration") {
                        Registration(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .background(Color.Gray)
    ) {


        Spacer(modifier = Modifier.padding(100.dp))
        Text(text = "Home Page")
        Button(onClick = {
            navController.navigate("login") {
                popUpTo("login") {
                    inclusive = true
                }
            }
        }) {
            Text(text = "Go to Login")

        }

    }

}

@Composable
fun Login(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .background(Color.Gray)
    ) {


        Spacer(modifier = Modifier.padding(100.dp))
        Text(text = "Login Page")

        Row{
            Button(onClick = {
                navController.navigate("screen1")
            }) {
                Text(text = "Go to Home")
            }

            Button(onClick = {
                navController.navigate("registration")
            }) {
                Text(text = "Go to Registration")
            }
        }

    }

}


@Composable
fun Registration(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .background(Color.Gray)
    ) {


        Spacer(modifier = Modifier.padding(100.dp))
        Text(text = "Registration Page")

        Row{
            Button(onClick = {
                navController.navigate("screen1")
            }) {
                Text(text = "Go to Home")
            }

        }

    }

}