package com.example.lab71

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class DownloadImageService : Service() {
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val url = intent?.getStringExtra(keyURLExtra)

        if (url == null) {
            stopSelf(startId)
            return START_NOT_STICKY
        }

        scope.launch {
            Log.i("LAB71LOG", "Current Thread: ${Thread.currentThread().name}")

            val imagePath = saveImage(
                this@DownloadImageService, filesDir, url
            ) ?: return@launch

            sendBroadcast(Intent("com.example.lab71.IMAGE_DOWNLOADED").apply {
                putExtra("IMAGE_PATH", imagePath)
            })

            Log.i("LAB71LOG", "Image path: $imagePath")

            stopSelf(startId)
        }

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        scope.cancel()
    }
}