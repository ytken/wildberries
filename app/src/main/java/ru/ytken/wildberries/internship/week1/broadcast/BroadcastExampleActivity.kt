package ru.ytken.wildberries.internship.week1.broadcast

import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_broadcast_example.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import ru.ytken.wildberries.internship.week1.R

// Класс демонстрирует использование BroadcastReceiver в приложении
// В данном примере intent-filter настроен на системные сообщения об изменении уровня громкости на устройстве
// На экране выводится уровень громкости в шкале прогресса (ProgressBar) и процентом (TextView)
// Такую функциональность можно увидеть, например, в приложении TikTok: такая же строка прогресса появляется
//          тонкой строкой над нижним меню при изменении уровня громкости во время воспроизведения видео

class BroadcastExampleActivity : AppCompatActivity(), VolumeReceiver.VolumeChangedListener {
    private val tagCurrentVolume = "TAG_CURRENT_VOLUME"
    private val tagPrevVolume = "TAG_PREVIOUS_VOLUME"

    private var currentVolume = 0
    private var previousVolume = 0

    private var mVolumeReceiver: VolumeReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast_example)

        setSupportActionBar(custom_toolbar_broadcast as Toolbar?)
        imageButtonToolbarClose.setOnClickListener { finish() }

        initReceiver()

        currentVolume = savedInstanceState?.getInt(tagCurrentVolume) ?: 0
        previousVolume = savedInstanceState?.getInt(tagPrevVolume) ?: 0
        changeVolume()
    }

    private fun initReceiver() {
        val mIntentFilter = IntentFilter("android.media.VOLUME_CHANGED_ACTION")
        mVolumeReceiver = VolumeReceiver()
        registerReceiver(mVolumeReceiver, mIntentFilter)
    }

    override fun onVolumeChanged(oldVolume: Int, newVolume: Int) {
        previousVolume = (oldVolume.toFloat() / 150 * 100).toInt()
        currentVolume = (newVolume.toFloat() / 150 * 100).toInt()
        changeVolume()
    }

    private fun changeVolume() {
        val anim = ProgressBarAnimation(progressBarShowVolume, previousVolume.toInt(), currentVolume)
        anim.duration = 250
        progressBarShowVolume.startAnimation(anim)
        textViewShowVolume.text = "$currentVolume%"
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mVolumeReceiver)
    }

    override fun onRestart() {
        super.onRestart()
        initReceiver()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(tagPrevVolume, previousVolume)
        outState.putInt(tagCurrentVolume, currentVolume)
        super.onSaveInstanceState(outState)
    }

}