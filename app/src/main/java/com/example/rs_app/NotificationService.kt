package com.example.rs_app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

class NotificationService : Application() {
    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel(
            "example_channel_id",
            "Example Channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}