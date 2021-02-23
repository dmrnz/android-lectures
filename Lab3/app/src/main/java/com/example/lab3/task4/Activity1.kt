package com.example.lab3.task4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab3.databinding.Task4Activity1Binding
import com.example.lab3.task2.ActivityAbout
import com.example.lab3.task3.Activity2

class Activity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = Task4Activity1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.task4Activity1ToSecondButton.setOnClickListener {
            startActivity(Intent(this, Activity2::class.java))
        }

        binding.task4Activity1ToSecondButtonNoHistory.setOnClickListener {
            startActivity(
                Intent(this, Activity2::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            )
        }

        binding.navView.root.setOnNavigationItemSelectedListener {
            startActivity(Intent(applicationContext, ActivityAbout::class.java))
            true
        }
    }
}
