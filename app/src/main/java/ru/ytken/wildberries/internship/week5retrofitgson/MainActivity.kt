package ru.ytken.wildberries.internship.week5retrofitgson

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import ru.ytken.wildberries.internship.week5retrofitgson.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        const val tagSuperhero = "TAG_SUPERHERO"
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var superheroAdapter: SuperheroAdapter
    private val vm: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        vm.listOfSuperheroes.observe(this) {
            binding.recyclerViewSuperheroes.apply {
                layoutManager = GridLayoutManager(this@MainActivity, 2)
                superheroAdapter = SuperheroAdapter(it) {
                    val intent = Intent(this@MainActivity, SuperheroActivity::class.java)
                    intent.putExtra(tagSuperhero, it)
                    startActivity(intent)
                }
                adapter = superheroAdapter
            }
        }

    }
}