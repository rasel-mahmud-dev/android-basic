import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.example.inactive.R
import kotlinx.coroutines.*

class TouchService : Service() {

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)

    override fun onCreate() {
        super.onCreate()
        // Start the foreground notification
        startForeground(1, createNotification("Touch service running..."))

        // Start the countdown task
        startCountdown()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY // Keep the service running
    }

    private fun startCountdown() {
        serviceScope.launch {
            while (true) {
                // Get current time or calculate countdown
                val currentTime = System.currentTimeMillis()
                Log.d("TouchService", "Time elapsed: $currentTime")

                // Perform the task you want to do every second
                // If you need to update the UI, you'd use a broadcast or update notification here

                // Delay for 1 second
                delay(1000)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancel the coroutine when the service is destroyed
        serviceJob.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null // Not binding this service to any component
    }

    private fun createNotification(text: String): Notification {
        val channelId = "TouchServiceChannel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, "Touch Service", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }

        return Notification.Builder(this, channelId)
            .setContentTitle("Touch Tracker")
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_launcher_background) // Replace with your icon
            .build()
    }
}
