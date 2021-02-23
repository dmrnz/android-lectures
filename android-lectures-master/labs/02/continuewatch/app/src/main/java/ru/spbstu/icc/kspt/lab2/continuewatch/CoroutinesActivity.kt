package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import ru.spbstu.icc.kspt.lab2.continuewatch.databinding.ActivityMainBinding

class CoroutinesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        lifecycle.coroutineScope.launchWhenStarted {
            while (isActive) {
                changeText(binding)
                delay()
            }
        }

        setContentView(binding.root)
    }

    private suspend fun delay() {
        withContext(Dispatchers.Default) {
            delay(1000)
        }
    }

    private suspend fun changeText(binding: ActivityMainBinding) {
        withContext(Dispatchers.Main.immediate) {
            binding.textSecondsElapsed.post {
                binding.textSecondsElapsed.text =
                    getString(R.string.seconds_elapsed, secondsElapsed++)
            }
        }
    }

    private companion object {
        var secondsElapsed: Int = 0
    }
}
