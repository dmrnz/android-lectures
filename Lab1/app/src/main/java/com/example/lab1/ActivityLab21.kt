package com.example.lab1

import android.app.Activity
import android.os.Bundle
import android.util.Log

class ActivityLab21 : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("onCreate")
    }

    override fun onStart() {
        super.onStart()
        log("onStart")
    }

    override fun onResume() {
        super.onResume()
        log("onResume")
    }

    override fun onPause() {
        super.onPause()
        log("onPause")
    }

    override fun onStop() {
        super.onStop()
        log("onStop")
    }

    override fun onRestart() {
        super.onRestart()
        log("onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(str: String) {
        Log.d(DEBUG_TAG, str);
    }

    companion object {
        private const val DEBUG_TAG: String = "LAB2_1"
    }
}