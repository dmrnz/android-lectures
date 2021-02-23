package com.example.lab72

import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.lab72.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var receiver: DownloadReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        receiver = DownloadReceiver()
        registerReceiver(receiver, IntentFilter().apply {
            addAction("com.example.lab71.IMAGE_DOWNLOADED")
        })

        val imagePath = intent?.getStringExtra("IMAGE_PATH")

        if (imagePath != null) {
            binding.activityMainPath.text = getString(R.string.path, imagePath)
            binding.activityMainPath.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}