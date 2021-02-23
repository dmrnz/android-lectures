package com.example.lab6

import android.content.Context
import android.view.View
import android.widget.Toast
import com.example.lab6.databinding.ActivityMainBinding


fun visibilityChangeOnPressButton(binding: ActivityMainBinding) {
    binding.activityMainLoadImgBtn.visibility = View.GONE
    binding.activityMainImgProgressBar.visibility = View.VISIBLE
}

fun visibilityChangeOnDownloadSuccess(binding: ActivityMainBinding)  {
    binding.activityMainImgProgressBar.visibility = View.GONE
    binding.activityMainImg.visibility = View.VISIBLE
}

fun visibilityChangeOnDownloadError(binding: ActivityMainBinding, context: Context) {
    binding.activityMainLoadImgBtn.visibility = View.VISIBLE
    binding.activityMainImgProgressBar.visibility = View.GONE
    Toast.makeText(context, context.getString(R.string.download_error), Toast.LENGTH_LONG).show()
}

const val asyncTaskURL = "https://miro.medium.com/max/1424/1*w5kbt6MMyMDZdv5AaoMuaw.jpeg"
const val coroutinesURL = "https://img.devrant.com/devrant/rant/r_1060093_TS4dx.jpg"
const val picassoURL = "https://i.pinimg.com/736x/93/1b/78/931b789fd2d2e2c9f810a45d88a96f8f.jpg"
