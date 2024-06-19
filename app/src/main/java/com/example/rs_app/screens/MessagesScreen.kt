package com.example.rs_app.screens

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.rs_app.GlobalState
import com.example.rs_app.Message
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(FlowPreview::class)
@Composable
fun MessagesScreen(f: NavHostController, applicationContext: Context) {

    val firestore = FirebaseFirestore.getInstance()
    val messagesCollection = firestore.collection("messages")

    val coroutineScope = rememberCoroutineScope()

    val prefs by lazy {
        applicationContext.getSharedPreferences("prefs", MODE_PRIVATE)
    }

    val scrollPosition = prefs.getInt("scroll_position", 0)

    suspend fun handleFetchAll() {
        try {
            val messages = mutableListOf<Message>()
            val querySnapshot = messagesCollection.get().await()
            for (document in querySnapshot.documents) {
                val message = document.toObject<Message>() // Replace with your data class
                message?.let {
                    messages.add(it)
                }
            }

            GlobalState.messages = messages
        } catch (e: Exception) {
            println("Error adding user: ${e.message}")
        }
    }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            handleFetchAll()
        }
    }

    val messages = GlobalState.messages

    val lazyListState = rememberLazyListState(
        initialFirstVisibleItemIndex = scrollPosition
    )

    LaunchedEffect(lazyListState) {
        snapshotFlow {
            lazyListState.firstVisibleItemIndex
        }
            .debounce(500L)
            .collectLatest { index ->
                prefs
                    .edit()
                    .putInt("scroll_position", index)
                    .apply()
            }
    }

    Column(modifier = Modifier.padding(10.dp, 40.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Total ${messages.count()}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
            Button(onClick = {
                coroutineScope.launch {
                    handleFetchAll()
                }
            }) {
//                Text(text = "Refetch")

                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refetch list",
                    modifier = Modifier.width(20.dp)
                )
            }
        }
        MessageList(messages = messages, lazyListState = lazyListState)
    }
}

@Composable
fun MessageList(messages: List<Message>, lazyListState: LazyListState) {
    LazyColumn(
        state = lazyListState,
        modifier = Modifier.fillMaxSize()
    ) {
        items(messages) { message ->
            Row(
                modifier = Modifier
                    .padding(0.dp, 4.dp)
                    .height(IntrinsicSize.Min)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0x17FF5044))
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {

                    Text(
                        text = message.id,
                        fontSize = 12.sp,
                        color = Color(0xFF181818),
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        text = message.text,
                        fontSize = 14.sp,
                        color = Color(0xFF181818),
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "From: ${message.email}",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "Sent:",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    if (message.markAsNotified) {
                        Text(
                            text = "Read:  ",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    } else {
                        Text(
                            text = "Unread",
                            fontSize = 12.sp,
                            color = Color.Red,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }
            }
        }
    }
}