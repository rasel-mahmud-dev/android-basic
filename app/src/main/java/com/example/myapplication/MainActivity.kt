package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text

import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    // Request permission on Android M or higher
    override fun onCreate(saveInstanceStat: Bundle?) {
        super.onCreate(saveInstanceStat)
//        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {

                Text(text = "sdfffffffffffffffjklf")

                Row{
                    Button(onClick = {
                        Intent(applicationContext, RunningService::class.java).also {
                            it.action = RunningService.Actions.START.toString()
                            startService(it)
                        }
                    }) {
                        Text(text = "Start service")
                    }

                    Button(onClick = {
                        Intent(applicationContext, RunningService::class.java).also {
                            it.action = RunningService.Actions.STOP.toString()
                            startService(it)
                        }
                    }) {
                        Text(text = "STOP service")
                    }
                }


            }
        }
    }
}
    

