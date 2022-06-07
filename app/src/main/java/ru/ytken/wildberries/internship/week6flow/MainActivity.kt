package ru.ytken.wildberries.internship.week6flow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.ytken.wildberries.internship.week6flow.databinding.ActivityMainBinding

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