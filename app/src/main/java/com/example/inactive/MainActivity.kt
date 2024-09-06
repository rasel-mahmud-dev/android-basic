package com.example.inactive

import MyBackgroundService
import RsButton
import TouchService
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.inactive.ui.theme.InactiveTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InactiveTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                        RsButton(modifier = Modifier.padding(innerPadding), onClick = {
                            val startServiceIntent =
                                Intent(applicationContext, MyBackgroundService::class.java)
                            startService(startServiceIntent)
                            Log.d("MainActivity", "Background task started")
                        }, {
                            Text("Start background task")
                        })

                        RsButton(modifier = Modifier, onClick = {
                            Intent(applicationContext, RunningService::class.java).also {
                                it.action = RunningService.Actions.START.toString()
                                startService(it)
                            }
                        }) {
                            Text(text = "Start service")
                        }

                        RsButton(modifier = Modifier, onClick = {
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

        sharedPreferences = getSharedPreferences("TouchData", MODE_PRIVATE)

        // Automatically start the background service here instead of using a button
        startBackgroundServices()
    }

    private fun startBackgroundServices() {
        // Start RunningService
        Intent(applicationContext, RunningService::class.java).also {
            it.action = RunningService.Actions.START.toString()
            startService(it)
        }

        // Start TouchService
        val touchServiceIntent = Intent(this, TouchService::class.java)
        startForegroundService(touchServiceIntent)

        Log.d("MainActivity", "Background services started")
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val lastTouchTime = SystemClock.elapsedRealtime()
            sharedPreferences.edit().putLong("lastTouchTime", lastTouchTime).apply()
        }
        return super.onTouchEvent(event)
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(modifier = Modifier.padding(10.dp)) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Text(
            text = "Hello h fffffffffffff$name!",
            modifier = modifier
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InactiveTheme {
        Greeting("Android")
    }
}