package com.example.rs_app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun GradientBox() {
    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(android.graphics.Color.parseColor("#FF0000")), // Red
                        Color(android.graphics.Color.parseColor("#0000FF"))  // Blue
                    )
                )
            )
    )
}

