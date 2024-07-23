import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.rs_app.UserModel
import com.example.rs_app.components.CustomTextField
import com.example.rs_app.screens.GradientBox
import kotlinx.coroutines.launch


data class FormState2(
    var email: String = "rasel.mahmud.dev@gmail.com",
    var firstName: String = "Rasel",
    var lastName: String = "Mahmud",
    var phone: String = "23456754323435",
    var avatar: String = "https://rasel-portfolio.vercel.app/images/rasel-mahmud-dev.webp",
    var password: String = "123"
)

@Composable
fun RegisterScreen(navController: NavHostController) {
    var formState by remember { mutableStateOf(FormState2()) }

    LocalSoftwareKeyboardController.current?.hide()

    fun handleChangeValue(name: String, value: String) {
        formState = when (name) {
            "email" -> formState.copy(email = value)
            "firstName" -> formState.copy(firstName = value)
            "lastName" -> formState.copy(lastName = value)
            "phone" -> formState.copy(phone = value)
            "avatar" -> formState.copy(avatar = value)
            "password" -> formState.copy(password = value)
            else -> formState
        }
    }

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    suspend fun insertUser() {
        try {
            val newUser = UserModel(
                email = formState.email,
                phone = formState.phone,
                password = formState.password,
                avatar = formState.avatar,
                firstName = formState.firstName,
                lastName = formState.lastName
            )

            newUser.save()
            Toast.makeText(context, "User added successfully", Toast.LENGTH_SHORT).show()

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
                        text = "Registration",

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
                value = formState.firstName,
                label = "firstName",
                name = "firstName",
                onValueChange = { name, value -> handleChangeValue(name, value) }
            )


            CustomTextField(
                value = formState.lastName,
                label = "lastName",
                name = "lastName",
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
                    coroutineScope.launch {
                        insertUser()
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


