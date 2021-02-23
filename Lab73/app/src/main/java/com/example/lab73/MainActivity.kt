package com.example.lab73

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.BitmapFactory
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.example.lab73.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val connection = object : ServiceConnection {
        var messenger: Messenger? = null

        fun isActive() = messenger != null

        override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
//            Log.i("lab73log", "Service connected")
            messenger = Messenger(service)
        }

        override fun onServiceDisconnected(className: ComponentName?) {
//            Log.i("lab73log", "Service disconnected")
            messenger = null
        }
    }

    fun showPath(location: String) {
        binding.activityMainPath.text = location
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityMainStartedBtn.setOnClickListener {
            startService(Intent(this, DownloadImageBoundService::class.java).apply {
                putExtra(keyURLExtra, imageSVG2)
            })
        }

        val intent = Intent(this, DownloadImageBoundService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)

        val messenger = Messenger(ResponseHandler(this))

        binding.activityMainBoundBtn.setOnClickListener {
            if (!connection.isActive()) {
//                Log.i("lab73log", "Service is currently disconnected")
                return@setOnClickListener
            }

            val message = Message.obtain().apply {
                obj = imagePNG
                replyTo = messenger
                what = MessageWhat.LOAD_IMAGE.what
            }

            try {
                connection.messenger?.send(message)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }
}