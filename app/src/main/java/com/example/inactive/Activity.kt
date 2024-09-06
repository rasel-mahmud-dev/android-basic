import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyBackgroundService : Service() {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onCreate() {
        super.onCreate()
        Log.d("MyBackgroundService", "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyBackgroundService", "onStartCommand")
        scope.launch {
            // Perform your background task here
            while (true) {
                Log.d("MyBackgroundService", "Performing background task")
                delay(1000) // Adjust the delay as needed
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        Log.d("MyBackgroundService", "onDestroy")
    }

    override fun onBind(intent: Intent?): IBinder? = null
}