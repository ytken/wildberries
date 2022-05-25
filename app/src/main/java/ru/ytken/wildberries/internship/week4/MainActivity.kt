package ru.ytken.wildberries.internship.week4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import ru.ytken.wildberries.internship.week4.fragments.ChatListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, ChatListFragment())
            .commit()

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }
}