import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest

@Composable
fun HomeScreen(navController: NavHostController) {
    var auth by remember { mutableStateOf<Auth?>(null) }
    var topSpace by remember { mutableIntStateOf(0) }

    fun handleLogout() {
        auth = null
        // remove user from async storage
    }

    fun sayHiAll() {
        // Call your API
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFE0E0E0))) {
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF4C669F),
                            Color(0xFF3B5998),
                            Color(0xFF192F6A)
                        )
                    )
                )
                .padding(horizontal = 10.dp, vertical = 20.dp)
                .fillMaxWidth()
                .height(250.dp)
//                .clip(shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
        ) {
            if (auth != null) {
                Column(
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
//                    AsyncImage()
                    Image(
                        painter = rememberAsyncImagePainter(auth!!.avatar),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                    Text(auth!!.username)
                    Text(auth!!.phone)
                    Text(
                        text = "Update",
                        color = Color.Blue,
                        modifier = Modifier.clickable {
                            // Handle update profile navigation
                        }
                    )
                }
            } else {
                Text(
                    text = "Login",
                    color = Color.Blue,
                    modifier = Modifier.clickable {
                        // Handle login navigation
                        navController.navigate("login")
                    }

                )
            }

            Button(onClick = { sayHiAll() }) {
                Text("Say Hi All")
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            // Replace BarChart with your actual chart composable
            BarChart()

            Spacer(modifier = Modifier.height(20.dp))

            // Replace BarChart2 with your actual chart composable
            BarChart2()
        }

        Box(modifier = Modifier){
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = "https://rasel-portfolio.vercel.app/images/rasel-mahmud-dev.webp")
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                ),
                contentDescription = "Avatar Image",
                modifier = Modifier
                    .padding(16.dp)
                    .width(160.dp)
                    .height(160.dp)
                    .clip(CircleShape),

                contentScale = ContentScale.Crop
            )
        }


    }
}

@Composable
fun BarChart() {
    // Your bar chart implementation
}

@Composable
fun BarChart2() {
    // Your second bar chart implementation
}

data class Auth(
    val avatar: String,
    val username: String,
    val phone: String
)

