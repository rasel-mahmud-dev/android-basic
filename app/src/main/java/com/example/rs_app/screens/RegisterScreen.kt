import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.rs_app.UserModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun RegisterScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("rasel.mahmud.dev@gmail.com") }
    var phone by remember { mutableStateOf("3245675345") }
    var password by remember { mutableStateOf("123") }
    var avatar by remember { mutableStateOf("sdf") }
    var username by remember { mutableStateOf("Rasel Mahmud") }


    val coroutineScope = rememberCoroutineScope()
    suspend fun insertUser(user: UserModel) {
        try {
            user.save()
            println("User added successfully")
        } catch (e: Exception) {
            println("Error adding user: ${e.message}")
        }
    }

    Column {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = avatar,
            onValueChange = { avatar = it },
            label = { Text("Avatar") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                val newUser = UserModel(
                    email = email,
                    phone = phone,
                    password = password,
                    avatar = avatar,
                    username = username
                )
                coroutineScope.launch {
                    insertUser(newUser)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }
    }
}
