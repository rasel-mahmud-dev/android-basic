package com.example.rs_app

import com.google.firebase.Timestamp

data class Message(
    val id: String = "",
    val email: String = "",
    val text: String = "",
//    val createdAt: ,
    val markAsNotified: Boolean = false
)
