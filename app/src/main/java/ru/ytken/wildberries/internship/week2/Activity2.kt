package ru.ytken.wildberries.internship.week2

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_2.*

class Activity2 : AppCompatActivity() {
    private val LOGTAG = "Week2" + Activity2::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)
        Log.d(LOGTAG, "onCreate")

        buttonCloseActivity.setOnClickListener { finish() }
        buttonOpenAlertDialog.setOnClickListener {
            val builder = AlertDialog.Builder(Activity2@this)
            builder.setTitle("Custom AlertDialog")
                .setPositiveButton("Close",
                    DialogInterface.OnClickListener { dialog, i -> dialog.dismiss() })
            builder.create().show()
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