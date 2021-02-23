package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.spbstu.icc.kspt.lab2.continuewatch.databinding.ActivityMainBinding

class ThreadsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var counter: StoppableCounter

    @Volatile
    private lateinit var mainThread: Thread

    @Volatile
    private var mainThreadWorking: Boolean = true

    private class StoppableCounter {
        @Volatile
        private var sideThreadWorking = true

        private val thread = Thread {
            while (sideThreadWorking) {
                secondsElapsed++
                if (!sideThreadWorking) {
                    break
                }
                Thread.sleep(SLEEPING_TIMEOUT)
            }
        }

        fun start() {
            thread.start()
        }

        fun stop() {
            sideThreadWorking = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            secondsElapsed = it.getInt(SECONDS_ELAPSED_KEY)
        }
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        counter = StoppableCounter().apply { start() }
        mainThreadWorking = true
        mainThread = Thread {
            while (mainThreadWorking) {
                binding.textSecondsElapsed.post {
                    binding.textSecondsElapsed.text =
                        getString(R.string.seconds_elapsed, secondsElapsed)
                }
                if (!mainThreadWorking) {
                    break
                }
                Thread.sleep(SLEEPING_TIMEOUT)
            }
        }.apply { start() }
    }

    override fun onStop() {
        super.onStop()
        counter.stop()
        mainThreadWorking = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SECONDS_ELAPSED_KEY, secondsElapsed)
    }

    private companion object {
        @Volatile
        var secondsElapsed = 0
        const val SECONDS_ELAPSED_KEY = "SECONDS_ELAPSED"
        const val SLEEPING_TIMEOUT: Long = 1000
    }
}
