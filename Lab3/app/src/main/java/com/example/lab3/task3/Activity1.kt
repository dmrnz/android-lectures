package com.example.lab3.task3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab3.databinding.Task2Activity1Binding
import com.example.lab3.task2.ActivityAbout

class Activity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = Task2Activity1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.task2Activity1ToSecondButton.setOnClickListener {
            startActivity(Intent(this, Activity2::class.java))
        }

        binding.navView.root.setOnNavigationItemSelectedListener {
            startActivity(Intent(applicationContext, ActivityAbout::class.java))
            true
        }
    }
}
