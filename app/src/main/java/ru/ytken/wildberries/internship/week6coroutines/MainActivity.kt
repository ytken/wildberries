package ru.ytken.wildberries.internship.week6coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import ru.ytken.wildberries.internship.week6coroutines.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = MainFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerMain, fragment)
            .addToBackStack(null)
            .commit()
    }

}