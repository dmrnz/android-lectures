package com.example.lab6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab6.databinding.ActivityMainBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class PicassoDownloadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityMainLoadImgBtn.setOnClickListener {
            visibilityChangeOnPressButton(binding)

            Picasso.get().load(picassoURL).into(
                binding.activityMainImg,
                object : Callback {
                    override fun onSuccess() {
                        runOnUiThread {
                            visibilityChangeOnDownloadSuccess(binding)
                        }
                    }

                    override fun onError(e: Exception?) {
                        runOnUiThread {
                            visibilityChangeOnDownloadError(binding, this@PicassoDownloadActivity)
                        }
                    }
                })
        }
    }
}
