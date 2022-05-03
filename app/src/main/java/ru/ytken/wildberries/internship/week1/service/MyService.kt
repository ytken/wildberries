package ru.ytken.wildberries.internship.week1.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import ru.ytken.wildberries.internship.week1.R

class MyService : Service() {

    companion object {
        val TAG_STOP_PLAYING = 1
    }

    private var mediaPlayer: MediaPlayer? = null
    private var toActivityMessenger: Messenger? = null


    override fun onCreate() {
        super.onCreate()
        if (mediaPlayer == null)
            mediaPlayer = MediaPlayer.create(applicationContext, R.raw.miley_cyrus_wrecking_ball)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            toActivityMessenger?.send(Message().apply { what = TAG_STOP_PLAYING })
            stopPlayer()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        toActivityMessenger = intent?.getParcelableExtra("incomingMessenger")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer != null) {
            stopPlayer()
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun stopPlayer() {
        mediaPlayer!!.release()
        mediaPlayer = null
        stopSelf()
    }

}