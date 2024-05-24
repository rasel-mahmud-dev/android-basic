package com.example.myapplication

import android.app.Fragment
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


data class Post(
    val title: String,
    val author: String? = null, // Optional author name
    val content: String? = null, // Optional post content
    val imageUrl: String? = null // Optional image URL for the post
)


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val (posts, setPosts) = remember { mutableStateOf(
        listOf(
            Post("First Post", "John Doe", "This is the content of the first post."),
//            Post("Second Post", "Jane Smith", "This is the content of the second post."),
//            Post("Third Post", "Alice", "This is the content of the third post."),
//            Post("Fourth Post", "Bob", "This is the content of the fourth post."),
//            Post("Fifth Post", "Charlie", "This is the content of the fifth post.")
        )
    )}

    var showDialog by remember { mutableStateOf(false) }

    fun handlePostDelete(title: String) {
        setPosts(posts.filter { it.title != title })
    }

    fun handleAddPost() {
        val timestamp = LocalDateTime.now().toString()
        val newPost = Post(title = "Title $timestamp", author = "author", content = "content")
        setPosts(posts + newPost)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {

        Button(
            onClick = { handleAddPost() },
            modifier = Modifier
                .height(50.dp) // Adjust the size as needed
                .padding(0.dp, 6.dp),
        ) {
            Text(
                text = "Add",
                fontSize = 12.sp // Adjust the font size as needed
            )
        }


        Column {


            LazyColumn {
                items(posts) { post ->
                    PostItem(post = post, onDeleteClick = { handlePostDelete(post.title) })
                }
            }
        }


        FloatingApp(modifier)


        Button(
            onClick = { showDialog = true },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Open Dialog")
        }

        CustomDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false }
        )


    }
}
val linearGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFF6586F0), // Start color
        Color(0xFF95A4F5), // Center color
        Color(0xFFFFB878)  // End color
    ),
    start = Offset.Zero,
    end = Offset.Infinite
)

@Composable
fun CustomDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Dialog Title") },
            text = {

                Column {
                    Text(text = "Dialog Message")

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = {}) {
                            Text(text = "Button 1")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(onClick = {}) {
                            Text(text = "Button 2")
                        }
                    }
                }

                   },
            confirmButton = {
                Button(onClick = onDismiss) {
                    Text(text = "Close")
                }
            }
        )
    }
}

@Composable
fun PostItem(post: Post, onDeleteClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .background(
                brush = linearGradient,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .padding(10.dp)
    ) {

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
            ){
            Text(
                text = post.title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp // Adjust the font size as needed
            )

            Button(
                onClick = onDeleteClick,
                modifier = Modifier
                    .height(50.dp) // Adjust the size as needed
                    .padding(0.dp, 6.dp),
            ) {
                Text(
                    text = "Delete",
                    fontSize = 12.sp // Adjust the font size as needed
                )
            }
        }
        post.author?.let {
            Text(text = "Author: $it")
        }
        post.content?.let {
            Text(text = it)
        }

    }
}
@Composable
fun FloatingApp(modifier:  Modifier) {
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
//            .elevation(8.dp) // Set the elevation to make it appear floating
    ) {
        Column(
            modifier = Modifier
                .background(
                    brush = linearGradient
                )
                .padding(8.dp)
        ) {
            // Your column content here
            Text(text = "hisdflksd klsdjf")
        }
    }

}

class Screen1Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_screen1, container, false)
    }
}

class Screen2Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_screen2, container, false)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}