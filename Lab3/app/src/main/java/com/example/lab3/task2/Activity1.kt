package com.example.lab3.task2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab3.databinding.Task2Activity1Binding

class Activity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = Task2Activity1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.task2Activity1ToSecondButton.setOnClickListener {
            startActivityForResult(Intent(this, Activity2::class.java), START_ACTIVITY)
        }

        binding.navView.root.setOnNavigationItemSelectedListener {
            startActivity(Intent(applicationContext, ActivityAbout::class.java))
            true
        }
    }
}

const val ACTIVITIES_TO_DESTROY = "ACTIVITIES_TO_DESTROY"
const val START_ACTIVITY = 0
