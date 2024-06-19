package com.example.rs_app

import HomeScreen
import RegisterScreen
import UpdateProfileScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rs_app.screens.LoginScreen
import com.example.rs_app.ui.theme.RsAppTheme
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()
        setContent {
            RsAppTheme {
                val navController = rememberNavController()
                LoadAuthInfo()
                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") { entry ->
//                        val text = entry.savedStateHandle.get<String>("my_text")
                        HomeScreen(navController)
                    }
                    composable("login") {
                        LoginScreen(navController)
                    }
                    composable("registration") {
                        RegisterScreen(navController)
                    }

                    composable("update-profile") {
                        UpdateProfileScreen(navController)
                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name! ",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RsAppTheme {
        Greeting("H")
    }
}



@Composable
fun LoadAuthInfo() {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val authUser = AuthPreferences.getAuthUser(context)
            if (authUser != null) {
                GlobalAuthState.authUser = authUser
            } else {
                GlobalAuthState.authUser = authUser
                print("Please login...")
            }
        }
    }
}
