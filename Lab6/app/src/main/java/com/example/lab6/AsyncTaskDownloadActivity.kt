@file:Suppress("DEPRECATION")

package com.example.lab6

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab6.databinding.ActivityMainBinding
import java.io.IOException
import java.net.URL

class AsyncTaskDownloadActivity : AppCompatActivity() {

    class ImageDownloadTask(private val binding: ActivityMainBinding, private val ctx: AsyncTaskDownloadActivity) : AsyncTask<String, Unit, Bitmap>() {
        override fun onPreExecute() {
            visibilityChangeOnPressButton(binding)
        }

        override fun doInBackground(vararg params: String): Bitmap? {
            if (params.isEmpty() || params.size > 1) {
                throw IllegalArgumentException("Amount of URLs is not correct in $params")
            }

            val url = params[0]
            var bitmap: Bitmap? = null

            try {
                bitmap = BitmapFactory.decodeStream(URL(url).openStream())
            } catch (e: IOException) {
            }

            return bitmap
        }

        override fun onPostExecute(result: Bitmap?) {
            if (result == null) {
                visibilityChangeOnDownloadError(binding, ctx)
                return
            }

            binding.activityMainImg.setImageBitmap(result)
            visibilityChangeOnDownloadSuccess(binding)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityMainLoadImgBtn.setOnClickListener {
            ImageDownloadTask(binding, this).execute(asyncTaskURL)
        }
    }
}
