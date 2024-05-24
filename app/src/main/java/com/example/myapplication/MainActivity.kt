package com.example.myapplication
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Button(onClick = {
                    Intent(this, SecondActivity::class.java).also {
                        startActivity(it)
                    }
                }) {
                    Text(text = "Open Second Activity")
                }
            }
        }
    }
}
