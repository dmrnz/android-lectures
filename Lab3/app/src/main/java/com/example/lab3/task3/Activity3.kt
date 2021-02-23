package com.example.lab3.task3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab3.databinding.Task2Activity3Binding
import com.example.lab3.task2.ActivityAbout

class Activity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = Task2Activity3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.task2Activity3ToFirstButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    Activity1::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
        }

        binding.task2Activity3ToSecondButton.setOnClickListener {
            finish()
        }

        binding.navView.root.setOnNavigationItemSelectedListener {
            startActivity(Intent(applicationContext, ActivityAbout::class.java))
            true
        }
    }
}
