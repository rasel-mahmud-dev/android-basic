package com.example.myapplication

import android.content.pm.PackageManager
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.viewinterop.AndroidView

import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

     // Request permission on Android M or higher
    override fun onCreate(savedInstanceState) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Check for SYSTEM_ALERT_WINDOW permission
        if (checkSelfPermission(android.Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED) {
            // Request permission if needed (implementation omitted for brevity)
            return
        }

        setContent {
            MyApplicationTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val context = LocalContext.current
    val windowManager = context.getSystemService(WindowManager::class.java)!!
    val layoutParams = WindowManager.LayoutParams().apply {
        type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY // Overlay type
        format = PixelFormat.TRANSLUCENT
        flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        gravity = Gravity.CENTER
        width = 223 // Match parent width
        height = 234 // Match parent height
    }

    val overlayView = rememberOverlayView(windowManager, layoutParams)

    // Render the overlay view with Compose content
    AndroidView(
        factory = { overlayView },
        modifier = Modifier.fillMaxSize()
    )
}

    @Composable
    fun rememberOverlayView(
        windowManager: WindowManager,
        layoutParams: WindowManager.LayoutParams
    ): View {
        val context = LocalContext.current
        val overlayView = remember {
            FrameLayout(context).apply {
                    setContent {
                    ComposeOverlay() // Define your Compose content here
                }
            }
        }

        // Handle view lifecycle and cleanup
        DisposableEffect(
            key1 = overlayView,
            key2 = windowManager
        ) {
            onDispose {
                windowManager.removeView(overlayView)
            }
        }

        // Add the view to the window manager only once
        LaunchedEffect(overlayView) {
            if (overlayView.parent == null) {
                windowManager.addView(overlayView, layoutParams)
            }
        }

        return overlayView
    }

// Define your Compose content for the overlay here (ComposeOverlay composable)
@Composable
fun ComposeOverlay() {
    // Your Compose UI for the overlay content
}
