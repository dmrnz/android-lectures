package com.example.lab71

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab71.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityMainLoadImgBtn.setOnClickListener {
            startService(Intent(this, DownloadImageService::class.java).apply {
                putExtra(keyURLExtra, imagePNG)
            })
//            startService(Intent(this, DownloadImageService::class.java).apply {
//                putExtra(keyURLExtra, imageJPG)
//            })
//            startService(Intent(this, DownloadImageService::class.java).apply {
//                putExtra(keyURLExtra, imageSVG)
//            })
//            startService(Intent(this, DownloadImageService::class.java).apply {
//                putExtra(keyURLExtra, imageWEBP)
//            })
//            startService(Intent(this, DownloadImageService::class.java).apply {
//                putExtra(keyURLExtra, imageAVIF)
//            })
//            startService(Intent(this, DownloadImageService::class.java).apply {
//                putExtra(keyURLExtra, imageSVG2)
//            })
        }
    }
}
