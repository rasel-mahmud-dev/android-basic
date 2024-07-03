package com.example.rs_app.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(date: Date): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val format2 = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return format.format(date) + " " + format2.format(date)
}