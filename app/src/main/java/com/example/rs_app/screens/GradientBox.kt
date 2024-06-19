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
fun GradientBox(content: @Composable () -> Unit, modifier: Modifier, colors: List<Color>) {
    Box(
        modifier
            .height(200.dp)
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = colors
//                    colors = listOf(
//                        Color(android.graphics.Color.parseColor(colors.toString())), // Red
//                        Color(android.graphics.Color.parseColor(colors.toString()))  // Blue
//                    )
                )
            )
    ) {
        content()
    }
}

