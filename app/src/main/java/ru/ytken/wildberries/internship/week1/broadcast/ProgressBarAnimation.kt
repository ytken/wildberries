package ru.ytken.wildberries.internship.week1.broadcast

import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ProgressBar

class ProgressBarAnimation(progressBar: ProgressBar, from: Int, to: Int): Animation() {
    val progressBar = progressBar
    val from = from
    val to = to

    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        val value: Float = from + (to - from) * interpolatedTime
        progressBar.progress = value.toInt()
    }
}