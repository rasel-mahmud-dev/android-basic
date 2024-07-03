import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.rs_app.AuthPreferences
import com.example.rs_app.AuthUser
import com.example.rs_app.GlobalAuthState
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


    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val result = UserModel.findById(userId)
//                if (result != null) {
//                    formState = formState.copy(
//                        email = result.email,
//                        phone = result.phone,
//                        avatar = result.avatar,
//                        username = result.username,
////                        password = result.password,
//                    )
//                }
            } catch (e: Exception) {
                println("Error adding user: ${e.message}")
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        println(uri)
//        selectedImageUri = uri
    }

    val context = LocalContext.current
    suspend fun handleUpdateUser() {
        try {
//            val user = UserModel.update(
//                userId, mapOf(
//                    "email" to formState.email,
//                    "phone" to formState.phone,
//                    "password" to formState.password,
//                    "avatar" to formState.avatar,
//                    "username" to formState.username
//                )
//            )
//
//            if (user != null) {
//                Toast.makeText(context, "User update successfully", Toast.LENGTH_SHORT).show()
//                val a = AuthUser(
//                    email = user.email,
//                    id = user.id,
//                    username = user.username,
////                    phone = user.phone,
//                    avatar = user.avatar,
//                )
//                GlobalAuthState.authUser = a
//                AuthPreferences.saveAuthUser(context, a)
//            }
        } catch (e: Exception) {
            coroutineScope.launch {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
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

            Button(onClick = { launcher.launch("image/*") },
                content = {
                    Icon(
                        imageVector = Icons.Default.AddPhotoAlternate,
                        contentDescription = "Pick a Photo",
                        modifier = Modifier.width(20.dp)
                    )

                    Text("Pick a Photo")
                })

//            PhotoPickerScreen()


            CustomTextField(
                value = formState.avatar,
                label = "avatar",
                name = "avatar",
                onValueChange = { name, value -> handleChangeValue(name, value) }
            )



            Button(
                onClick = {
                    coroutineScope.launch {
                        handleUpdateUser()
                    }
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
