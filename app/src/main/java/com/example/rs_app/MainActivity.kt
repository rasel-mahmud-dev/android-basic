package com.example.rs_app

import App
import HomeScreen
import RegisterScreen
import UpdateProfileScreen
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rs_app.screens.LoginScreen
import com.example.rs_app.screens.MessagesScreen

import com.example.rs_app.ui.theme.RsAppTheme
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import com.example.rs_app.screens.AuthenticatorScreen
import com.example.rs_app.utils.NotificationUtil


class MainActivity : ComponentActivity() {

    private lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
//        val isOk = checkOverlayPermission()
//        println("Permitted: $isOk")


        FirebaseApp.initializeApp(this)
        app = App(applicationContext)

        enableEdgeToEdge()
        setContent {
            RsAppTheme {
                val navController = rememberNavController()
                LoadAuthInfo()


                Column {
                    Row {
                        Button(onClick = {
                            Intent(applicationContext, RunningService::class.java).also {
                                it.action = RunningService.Actions.START.toString()
                                startService(it)
                            }
                        }) {
                            Text(text = "Start service")
                        }

                        Button(onClick = {
                            Intent(applicationContext, RunningService::class.java).also {
                                it.action = RunningService.Actions.STOP.toString()
                                stopService(it)
                            }
                        }) {
                            Text(text = "STOP service")
                        }
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = "authenticator"
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

                    composable("messages") {
                        MessagesScreen(navController, applicationContext)
                    }

                    composable("authenticator") {
                        AuthenticatorScreen(navController, applicationContext)
                    }


                    composable(
                        route = "update-profile/{userId}",
                        arguments = listOf(
                            navArgument("userId") { type = NavType.StringType },
                        )
                    ) { entry ->
                        UpdateProfileScreen(
                            navController = navController,
                            userId = entry.arguments?.getString("userId") ?: ""
                        )
                    }


                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        app.clear()
    }

    private fun checkOverlayPermission(): Boolean {
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivity(intent)
            return false
        } else {
            return true
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
//                GlobalAuthState.authUser = authUser
//                print("Please login...")
            }
        }
    }
}
