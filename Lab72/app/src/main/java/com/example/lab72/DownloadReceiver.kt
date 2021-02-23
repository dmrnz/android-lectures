package com.example.lab72

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class DownloadReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("LAB71LOG", "Received intent")

        val imagePath = intent?.getStringExtra("IMAGE_PATH")
            ?: return

        val startActivityWithImagePathIntent = Intent(context, MainActivity::class.java).apply {
            putExtra("IMAGE_PATH", imagePath)
        }

        context?.startActivity(startActivityWithImagePathIntent)
    }
}