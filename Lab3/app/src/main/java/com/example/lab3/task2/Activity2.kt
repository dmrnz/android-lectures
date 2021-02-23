package com.example.lab3.task2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab3.databinding.Task2Activity2Binding

class Activity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = Task2Activity2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.task2Activity2ToFirstButton.setOnClickListener {
            finish()
        }

        binding.task2Activity2ToThirdButton.setOnClickListener {
            startActivityForResult(Intent(this, Activity3::class.java), START_ACTIVITY)
        }

        binding.navView.root.setOnNavigationItemSelectedListener {
            startActivity(Intent(applicationContext, ActivityAbout::class.java))
            true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == START_ACTIVITY && resultCode == RESULT_OK && data != null) {
            val activitiesToDestroy = data.getIntExtra(ACTIVITIES_TO_DESTROY, 0)

            if (activitiesToDestroy > 0) {
                setResult(RESULT_OK, Intent().apply {
                    putExtra(ACTIVITIES_TO_DESTROY, activitiesToDestroy - 1)
                })
                finish()
            }
        }
    }
}
