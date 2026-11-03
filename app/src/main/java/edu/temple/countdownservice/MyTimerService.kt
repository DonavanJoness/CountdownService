package edu.temple.countdownservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class MyTimerService : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.Main)
    private var timerJob: Job? = null

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val startValue = intent?.getIntExtra(TimerKey, 0) ?: 0

        timerJob?.cancel()
        timerJob = serviceScope.launch {
            for (i in startValue downTo 0) {
                Log.d("MyTimerService", "Countdown: $i")
                if (i > 0) {
                    delay(1.seconds)
                }
            }
            stopSelf(startId)
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        serviceScope.cancel()
        super.onDestroy()
    }
}
