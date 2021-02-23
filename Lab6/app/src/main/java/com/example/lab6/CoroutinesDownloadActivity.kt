package com.example.lab6

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.lab6.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL

class CoroutinesDownloadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityMainLoadImgBtn.setOnClickListener {
            clickHandler(binding)
        }
    }

    private fun clickHandler(binding: ActivityMainBinding) {
        visibilityChangeOnPressButton(binding)

        lifecycleScope.launchWhenStarted {
            var bitmap: Bitmap? = null

            withContext(Dispatchers.IO) {
                try {
                    bitmap = BitmapFactory.decodeStream(URL(coroutinesURL).openStream())
                } catch (e: IOException) {
                }
            }

            if (bitmap == null) {
                runOnUiThread {
                    visibilityChangeOnDownloadError(binding, this@CoroutinesDownloadActivity)
                }
                return@launchWhenStarted
            }

            withContext(Dispatchers.Main) {
                binding.activityMainImg.setImageBitmap(bitmap)
                runOnUiThread {
                    visibilityChangeOnDownloadSuccess(binding)
                }
            }
        }
    }
}
