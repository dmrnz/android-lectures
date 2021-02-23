package com.example.lab3.task3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab3.databinding.Task2Activity2Binding
import com.example.lab3.task2.ActivityAbout

class Activity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = Task2Activity2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.task2Activity2ToFirstButton.setOnClickListener {
            finish()
        }

        binding.task2Activity2ToThirdButton.setOnClickListener {
            startActivity(Intent(this, Activity3::class.java))
        }

        binding.navView.root.setOnNavigationItemSelectedListener {
            startActivity(Intent(applicationContext, ActivityAbout::class.java))
            true
        }
    }
}
