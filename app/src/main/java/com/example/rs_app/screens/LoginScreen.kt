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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.rs_app.AuthPreferences
import com.example.rs_app.AuthUser
import com.example.rs_app.GlobalAuthState
import com.example.rs_app.UserModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController) {

    val context = LocalContext.current

    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

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
            } else {
                GlobalAuthState.authUser = null
            }

        } catch (e: Exception) {
            print(e)
//            _loginStatus.value = "Login failed: ${e.message}"
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {

        Spacer(modifier = Modifier.padding(100.dp))
        Text(text = "Login Page")

        // Your GradientBox Composable goes here
        // GradientBox()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            TextField(
                value = email,
                onValueChange = setEmail,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = setPassword,
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
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
                    handleLogin(email)
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
        if(authUser != null){
            Text("Logged in as: ${authUser?.username}")
            Text("Email: ${authUser?.email}")
        } else {
            Text("Not logged in")
        }



    }
}


