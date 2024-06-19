package com.example.rs_app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.rs_app.AuthPreferences
import com.example.rs_app.AuthUser
import com.example.rs_app.GlobalAuthState
import com.example.rs_app.UserModel
import com.example.rs_app.components.CustomTextField

import kotlinx.coroutines.launch


data class LoginFormState(
    var email: String = "",
    var password: String = ""
)


@Composable
fun LoginScreen(navController: NavHostController) {

    val context = LocalContext.current

    var formState by remember { mutableStateOf(LoginFormState()) }

    val coroutineScope = rememberCoroutineScope()

    fun handleChangeValue(name: String, value: String) {
        formState = when (name) {
            "email" -> formState.copy(email = value)
            "password" -> formState.copy(password = value)
            else -> formState
        }
    }


    suspend fun handleLogin(email: String) {
        try {
            val user = UserModel.findByEmail(email)
            if (user != null) {
                val authUser = AuthUser(
                    email = user.email,
                    username = user.username,
                    avatar = user.avatar
                )
                GlobalAuthState.authUser = authUser
                AuthPreferences.saveAuthUser(context, authUser)

                println("User logged in: ${GlobalAuthState.authUser}")
                navController.navigate("home")
            } else {
                GlobalAuthState.authUser = null
            }

        } catch (e: Exception) {
            print(e)
//            _loginStatus.value = "Login failed: ${e.message}"
        }
    }

    Column {
        GradientBox(
            {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "User Login",

                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }, modifier = Modifier.padding(0.dp),
            listOf(
                Color(0xFF4C669F),
                Color(0xFF3B5998),
                Color(0xFF192F6A)
            )
        )

        Column(modifier = Modifier.padding(14.dp, 0.dp)) {

            CustomTextField(
                value = formState.email,
                name = "email",
                label = "Email",
                onValueChange = { name, value -> handleChangeValue(name, value) }
            )

            CustomTextField(
                value = formState.password,
                name = "password",
                label = "Password",
                onValueChange = { name, value -> handleChangeValue(name, value) }
            )

        }
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                navController.navigate("screen1")
            }) {
                Text(text = "Go to Home")
            }

            Button(onClick = {
                coroutineScope.launch {
                    handleLogin(formState.email)
                }

            }) {
                Text(text = "Submit")
            }

            Button(onClick = {
                navController.navigate("registration")
            }) {
                Text(text = "Registration")
            }
        }


        // Display authentication status and user info
        val authUser = GlobalAuthState.authUser
        if (authUser != null) {
            Text("Logged in as: ${authUser?.username}")
            Text("Email: ${authUser?.email}")
        } else {
            Text("Not logged in")
        }


    }
}


