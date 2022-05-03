package ru.ytken.wildberries.internship.week1.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler

class VolumeReceiver: BroadcastReceiver() {
    private val tagNewVolume = "android.media.EXTRA_VOLUME_STREAM_VALUE"
    private val tagOldVolume = "android.media.EXTRA_PREV_VOLUME_STREAM_VALUE"

    interface VolumeChangedListener {
        fun onVolumeChanged(oldVolume: Int, newVolume: Int)
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        val newVolume = intent?.getIntExtra(tagNewVolume, 0)
        val oldVolume = intent?.getIntExtra(tagOldVolume, 0)

        if (context is VolumeChangedListener)
            if (oldVolume != null && newVolume != null)
                context.onVolumeChanged(oldVolume, newVolume)
    }
}