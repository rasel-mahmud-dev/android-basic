package com.example.myapplication
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {


    private fun isRunning(): Boolean{
        val manager  = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (servce in manager.getRunningServices(Int.MAX_VALUE)){
            if(FloatingWindow::class.java.name == servce.service.className){
                return true
            }
        }

        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {

                var isServiceRunning  = false

                Button(onClick = {

//                    Intent(this, FloatingWindow::class.java).also {
//                        stopService(it)
//                    }

                    if(isRunning()){
                        println("Stopped...")
                        stopService(Intent(this@MainActivity, FloatingWindow::class.java))
                    }
                    println("Start...")
                    startService(Intent(this@MainActivity, FloatingWindow::class.java))
                    finish()

                }) {
                    Text(text = "Open Second Activity")
                }
            }
        }
    }
}
