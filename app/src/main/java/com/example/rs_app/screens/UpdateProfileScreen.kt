import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.rs_app.UserModel
import com.example.rs_app.components.CustomTextField
import com.example.rs_app.screens.GradientBox
import kotlinx.coroutines.launch


data class FormState(
    var email: String = "",
    var username: String = "",
    var phone: String = "",
    var avatar: String = "",
    var password: String = ""
)

@Composable
fun UpdateProfileScreen(navController: NavHostController, userId: String) {

    var formState by remember { mutableStateOf(FormState()) }

    fun handleChangeValue(name: String, value: String) {
        formState = when (name) {
            "email" -> formState.copy(email = value)
            "username" -> formState.copy(username = value)
            "phone" -> formState.copy(phone = value)
            "avatar" -> formState.copy(avatar = value)
            "password" -> formState.copy(password = value)
            else -> formState
        }
    }

    val coroutineScope = rememberCoroutineScope()
    suspend fun insertUser(user: UserModel) {
        try {
            user.save()
            println("User added successfully")
        } catch (e: Exception) {
            println("Error adding user: ${e.message}")
        }
    }


    LaunchedEffect(Unit) {
        coroutineScope.launch {
            // Fetch initial data here (e.g., user information from the database)
//            val initialData = // your code to fetch data
            try {
                val result = UserModel.findAll()
                println(result)
            } catch (e: Exception) {
                println("Error adding user: ${e.message}")
            }

//
//                // Update formState with the fetched data
//                formState = formState.copy(
//                    email = initialData.email,
//                    username = initialData.username,
//                    phone = initialData.phone,
//                    avatar = initialData.avatar
//                )

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
                value = formState.username,
                label = "username",
                name = "username",
                onValueChange = { name, value -> handleChangeValue(name, value) }
            )


            CustomTextField(
                value = formState.phone,
                label = "Phone",
                name = "phone",
                onValueChange = { name, value -> handleChangeValue(name, value) }
            )

            CustomTextField(
                value = formState.password,
                label = "password",
                name = "password",
                onValueChange = { name, value -> handleChangeValue(name, value) }
            )

            CustomTextField(
                value = formState.avatar,
                label = "avatar",
                name = "avatar",
                onValueChange = { name, value -> handleChangeValue(name, value) }
            )



            Button(
                onClick = {
//                    val newUser = UserModel(
//                        email = email,
//                        phone = phone,
//                        password = password,
//                        avatar = avatar,
//                        username = username
//                    )
//                    coroutineScope.launch {
//                        insertUser(newUser)
//                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }

            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            Button(
                onClick = { navController.navigate("home") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Home")
            }
        }
    }
}
