package com.example.inactive

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class RunningService : Service() {

    // Create a job for the CoroutineScope so we can cancel it later
    private var serviceJob: Job? = null
    private val serviceScope = CoroutineScope(Dispatchers.IO)

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stop()
        }
        return START_NOT_STICKY // Don't restart the service automatically after it is killed
    }

    @SuppressLint("ForegroundServiceType")
    private fun start() {
        Log.d("RunningService", "Service started")

        // Cancel any previous job before starting a new one
        serviceJob?.cancel()

        // Start a new countdown task
        serviceJob = startCountdown()
    }

    private fun startCountdown(): Job {
        return serviceScope.launch {
            while (isActive) { // Use isActive to check if the coroutine is still running
                // Get current time or calculate countdown
                val currentTime = System.currentTimeMillis()
                Log.d("RunningService", "Time elapsed: $currentTime")

                // Perform the task you want to do every second

                // Delay for 1 second
                delay(1000)
            }
        }
    }

    private fun stop() {
        Log.d("RunningService", "Service stopped")
        stopForeground(true)
        stopSelf()

        // Cancel the job to stop the countdown
        serviceJob?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()

        // Clean up: cancel the job if the service is destroyed
        serviceJob?.cancel()
    }

    enum class Actions {
        START, STOP
    }
}
