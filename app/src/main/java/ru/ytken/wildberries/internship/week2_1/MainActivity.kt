package ru.ytken.wildberries.internship.week2_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    private val LOGTAG = "Week2_1" + MainActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(LOGTAG, "onCreate")

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, Fragment1())
            .commit()
    }

    override fun onStart() {
        super.onStart()
        Log.d(LOGTAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(LOGTAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(LOGTAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(LOGTAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOGTAG, "onDestroy")
    }
}