package com.example.rs_app.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RsSwitch(
    value: Boolean,
    onValueChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    name: String? = null,
    readOnly: Boolean = false
) {
    var switchValue by remember { mutableStateOf(value) }
    val translateX by animateFloatAsState(targetValue = if (switchValue) 20f else 0f)

    Box(
        modifier = modifier
            .width(45.dp)
            .height(25.dp)
            .padding(2.dp)
            .background(
                color = if (switchValue) MaterialTheme.colorScheme.primary else Color(0xFFDDDDDD),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(enabled = !readOnly) {
                switchValue = !switchValue
                onValueChange(switchValue)
            }
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .offset(x = translateX.dp)
                .background(color = Color.White, shape = CircleShape)
                .shadow(elevation = 3.dp, shape = CircleShape)
        )
    }
}
