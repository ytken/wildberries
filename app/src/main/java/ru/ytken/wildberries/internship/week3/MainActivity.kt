package ru.ytken.wildberries.internship.week3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.main_container.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_container)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, MainFragment())
            .addToBackStack(null)
            .commit()
    }
}