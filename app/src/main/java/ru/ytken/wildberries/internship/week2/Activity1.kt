package ru.ytken.wildberries.internship.week2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_1.*

class Activity1 : AppCompatActivity() {
    private val LOGTAG = "Week2" + Activity1::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_1)
        Log.d(LOGTAG, "onCreate")

        buttonOpenNextActivity.setOnClickListener {
            startActivity(Intent(this, Activity2::class.java))
        }
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