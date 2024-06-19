package com.example.rs_app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (name: String, value: String) -> Unit,
    label: String,
    name: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(0.dp, 8.dp)
            .border(width = 0.dp, color = Color.Transparent)
    ) {

        Text(label, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        BasicTextField(

            value = value,
            onValueChange = { newValue -> onValueChange(name, newValue) },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),

            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
                .border(
                    width = if (isFocused) 2.dp else 1.2.dp,
                    color = if (isFocused) Color(0xFF6200EE) else Color(0xFFE91E63),
                    shape = RoundedCornerShape(14.dp)
                )
                .background(Color.Transparent)
                .padding(16.dp) // Padding inside the text field
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            cursorBrush = SolidColor(Color.Black)
        )
    }
}
