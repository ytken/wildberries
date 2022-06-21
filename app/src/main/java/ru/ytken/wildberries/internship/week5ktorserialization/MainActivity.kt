package ru.ytken.wildberries.internship.week5ktorserialization

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.ytken.wildberries.internship.week5ktorserialization.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}