package ru.ytken.wildberries.internship.week6threadshandlers

import android.os.Handler
import android.os.Looper
import android.os.Message

class IncomingPiHandler(private val fragment: MainFragment): Handler(Looper.getMainLooper()) {
    companion object {
        const val tagPiValueLeibniz = "TAG_PI_VALUE_LEIBNIZ"
        const val tagPiValueChud = "TAG_PI_VALUE_CHUD"
    }

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        val piValueLeibniz = msg.data.getString(tagPiValueLeibniz)
        val piValueChud = msg.data.getString(tagPiValueChud)
        if (piValueLeibniz != null) {
            fragment.setPiValueLeibniz(piValueLeibniz)
        }

        if (piValueChud != null) {
            fragment.setPiValueChud(piValueChud)
        }
    }
}