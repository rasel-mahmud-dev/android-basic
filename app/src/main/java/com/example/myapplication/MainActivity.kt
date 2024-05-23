package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

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
            Post("Second Post", "Jane Smith", "This is the content of the second post."),
            Post("Third Post", "Alice", "This is the content of the third post."),
            Post("Fourth Post", "Bob", "This is the content of the fourth post."),
            Post("Fifth Post", "Charlie", "This is the content of the fifth post.")
        )
    )}

    fun handlePostDelete(post: Post) {
        setPosts(posts.filter { it != post })
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {

        Column {
            for (post in posts) {
                PostItem(post = post, onDeleteClick = { handlePostDelete(post) })
            }
        }


    }
}

    @Composable
    fun PostItem(post: Post, onDeleteClick: () -> Unit) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .background(Color.LightGray)
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
                    modifier = Modifier.height(50.dp) // Adjust the size as needed
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


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}