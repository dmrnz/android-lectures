package com.example.lab73

import android.app.Service
import android.content.Intent
import android.os.FileUtils
import android.os.IBinder
import android.os.Messenger
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.io.File
import java.net.URL


class DownloadImageBoundService : Service() {
    private val scope = CoroutineScope(Dispatchers.IO)

    fun saveImage(url: String, saver: suspend (String) -> Unit) {
        scope.launch {
            Log.i("lab73log", "Current Thread: ${Thread.currentThread().name}")
            saveImage(url)?.let { url -> saver(url) }
        }
    }

    @Synchronized
    private fun saveImage(url: String): String? {
        val imageType = when {
            url.contains(""".${SupportedImageType.PNG.extension}""") -> SupportedImageType.PNG
            url.contains(""".${SupportedImageType.JPEG.extension}""") -> SupportedImageType.JPEG
            url.contains(""".${SupportedImageType.WEBP.extension}""") -> SupportedImageType.WEBP
            url.contains(""".${SupportedImageType.AVIF.extension}""") -> SupportedImageType.AVIF
            url.contains(""".${SupportedImageType.SVG.extension}""") -> SupportedImageType.SVG
            else -> return null
        }

        val imageTypePattern =
            SupportedImageType.PNG.extension +
                    "|${SupportedImageType.JPEG.extension}" +
                    "|${SupportedImageType.WEBP.extension}" +
                    "|${SupportedImageType.AVIF.extension}" +
                    "|${SupportedImageType.SVG.extension}"

        val index = (filesDir.list() ?: emptyArray()).count {
            Regex("""service_($imageTypePattern)_saved_picture_(\d+)\.($imageTypePattern)""").matches(
                it
            )
        }

        val filename =
            "service_${imageType.extension}_saved_picture_${index}.${imageType.extension}"

        this.openFileOutput(filename, MODE_PRIVATE).use { outputStream ->
            try {
                val inputStream = URL(url).openStream()
                FileUtils.copy(inputStream, outputStream)
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }

        return File(this.filesDir, filename).absolutePath
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val url = intent?.getStringExtra(keyURLExtra)

        if (url == null) {
            stopSelf(startId)
            return START_NOT_STICKY
        }

        saveImage(url) {
//            Log.i("lab73log", "Image location: $it")

            sendBroadcast(Intent("com.example.lab73.IMAGE_DOWNLOADED").apply {
                putExtra("IMAGE_PATH", it)
            })

            stopSelf(startId)
        }

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return Messenger(DownloadHandler(this)).binder
    }

    override fun onDestroy() {
        scope.cancel()
    }
}
