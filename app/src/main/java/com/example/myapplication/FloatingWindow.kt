package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button


class FloatingWindow : Service() {

    private lateinit var floatView: ViewGroup
    private lateinit var floatWindowLayoutParams: WindowManager.LayoutParams

    private var LAYOUT_TYPE: Int = 0
    private lateinit var windowManager: WindowManager
    private lateinit var btn: Button

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")

        return null;
    }



    override fun onCreate() {
        super.onCreate()

        // Get screen metrics
        val metrics = applicationContext.resources.displayMetrics
        val width = metrics.widthPixels
        val height = metrics.heightPixels

        // Initialize WindowManager
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        // Inflate the floating modal layout
        val inflater = baseContext.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        floatView = inflater.inflate(R.layout.floating_modal, null) as ViewGroup

        // Determine layout type based on API level
        LAYOUT_TYPE = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        }

        // Initialize the button
        btn = floatView.findViewById(R.id.btn2)
        btn.text = "Floating button..."

        // Setup layout parameters
        floatWindowLayoutParams = WindowManager.LayoutParams(
            (width * 0.55F).toInt(),
            (height * 0.15F).toInt(),
            LAYOUT_TYPE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        )

        // Set gravity to center
        floatWindowLayoutParams.gravity = Gravity.CENTER

        // Add the view to the window
        windowManager.addView(floatView, floatWindowLayoutParams)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove the view when the service is destroyed
        if (::floatView.isInitialized) {
            windowManager.removeView(floatView)
        }
    }

}