package com.example.rs_app.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.rs_app.R

object NotificationUtil {
    private const val CHANNEL_ID = "example_channel_id"
    private const val CHANNEL_NAME = "Example Channel"
    private const val CHANNEL_DESCRIPTION = "This is an example notification channel"

    fun showNotification(context: Context, title: String, message: String) {
        val notification = NotificationCompat.Builder(context, "channel_id")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .build()
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Use the current timestamp as the unique ID for the notification
        val notificationId = System.currentTimeMillis().toInt()

        notificationManager.notify(notificationId, notification)
    }
}
