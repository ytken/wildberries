package ru.ytken.wildberries.internship.week1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import ru.ytken.wildberries.internship.week1.broadcast.BroadcastExampleActivity
import ru.ytken.wildberries.internship.week1.content.ContentExampleActivity
import ru.ytken.wildberries.internship.week1.service.ServiceExampleActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonServiceExampleActivity.setOnClickListener {
            startActivity(Intent(this, ServiceExampleActivity::class.java))
        }

        buttonContentExampleActivity.setOnClickListener {
            startActivity(Intent(this, ContentExampleActivity::class.java))
        }

        buttonBroadcastExampleActivity.setOnClickListener {
            startActivity(Intent(this, BroadcastExampleActivity::class.java))
        }
    }
}