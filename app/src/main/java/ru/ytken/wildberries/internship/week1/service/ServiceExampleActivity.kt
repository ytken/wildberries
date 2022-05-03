package ru.ytken.wildberries.internship.week1.service

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_service_example.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import ru.ytken.wildberries.internship.week1.R

// Класс демонстрирует использование Service в приложении
// MyService является наследником Service и позволяет управлять вопроизведением локального аудио
//          файла. Воспроизведение не останавливается при закрытии этого Activity, только при закрытии
//          всего приложения. Сохранять состояние воспроизведения для вывода на экране позволяют
//          SharedPreferences.

class ServiceExampleActivity : AppCompatActivity() {

    private val TAG_MUSIC_PLAYING = "TAG_MUSIC_PLAYING"
    private var musicPlaying = false
    private lateinit var serviceIntent: Intent
    private lateinit var sharedPrefs: SharedPreferences

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            if (msg.what == MyService.TAG_STOP_PLAYING) {
                musicPlaying = false
                setImageButtonPlay()
            }
        }
    }

    private val incomingMessenger = Messenger(handler)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_example)

        setSupportActionBar(custom_toolbar_service as Toolbar?)
        imageButtonToolbarClose.setOnClickListener { finish() }

        sharedPrefs = this.getPreferences(Context.MODE_PRIVATE)
        serviceIntent = Intent(this, MyService::class.java)
        musicPlaying = sharedPrefs.getBoolean(TAG_MUSIC_PLAYING, false)

        setImageButtonPlay()

        imageButtonPlayTrack.setOnClickListener {
            musicPlaying = if (!musicPlaying) {
                playTrack()
                true
            } else {
                stopTrack()
                false
            }
            setImageButtonPlay()
        }
    }

    private fun setImageButtonPlay() {
        if (musicPlaying)
            imageButtonPlayTrack.setImageResource(R.drawable.ic_stop)
        else
            imageButtonPlayTrack.setImageResource(R.drawable.ic_play)
    }

    private fun playTrack() {
        startService(serviceIntent.putExtra("incomingMessenger", incomingMessenger))
    }

    private fun stopTrack() {
        stopService(serviceIntent)
    }

    override fun onDestroy() {
        with(sharedPrefs.edit()) {
            putBoolean(TAG_MUSIC_PLAYING, musicPlaying)
            apply()
        }
        super.onDestroy()
    }
}