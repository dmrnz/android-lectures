package com.example.lab3.task2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab3.databinding.Task2Activity3Binding

class Activity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = Task2Activity3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.task2Activity3ToFirstButton.setOnClickListener {
            setResult(RESULT_OK, Intent().apply {
                putExtra(ACTIVITIES_TO_DESTROY, 1)
            })
            finish()
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
