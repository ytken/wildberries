package ru.ytken.wildberries.internship.week6threadshandlers

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message

class IncomingTimeHandler(private val fragment: MainFragment): Handler(Looper.getMainLooper()) {
    companion object {
        const val tagTimeValue = "TAG_TIME_INT"
    }

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        val timeValue = msg.data.getInt(tagTimeValue)
        fragment.setTimeOnTimer(timeValue)
    }

}