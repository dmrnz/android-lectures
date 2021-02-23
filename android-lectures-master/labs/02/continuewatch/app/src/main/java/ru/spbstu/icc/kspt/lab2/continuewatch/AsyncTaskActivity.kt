@file:Suppress("DEPRECATION")

package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.spbstu.icc.kspt.lab2.continuewatch.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class AsyncTaskActivity : AppCompatActivity() {

    private lateinit var countTask: CountTask
    private lateinit var binding: ActivityMainBinding

    class CountTask(private val updateView: () -> Unit) : AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit) {
            while (!isCancelled) {
                publishProgress()
                TimeUnit.SECONDS.sleep(1)
                secondsElapsed++
            }
        }

        override fun onProgressUpdate(vararg values: Unit) {
            super.onProgressUpdate(*values)
            updateView()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        savedInstanceState?.let { secondsElapsed = it.getInt(SECONDS_ELAPSED_KEY) }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        countTask = CountTask {
            binding.textSecondsElapsed.text = getString(R.string.seconds_elapsed, secondsElapsed)
        }.execute() as CountTask
    }

    override fun onStop() {
        super.onStop()
        countTask.cancel(false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SECONDS_ELAPSED_KEY, secondsElapsed)
    }

    private companion object {
        var secondsElapsed = 0
        const val SECONDS_ELAPSED_KEY = "SECONDS_ELAPSED"
    }
}
