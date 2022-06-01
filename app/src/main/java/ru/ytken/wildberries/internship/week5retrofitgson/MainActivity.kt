package ru.ytken.wildberries.internship.week5retrofitgson

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import ru.ytken.wildberries.internship.week5retrofitgson.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        val tagId = "TAG_SUPERHERO_ID"
        val tagUrl = "TAG_SUPERHERO_URL"
    }
    private lateinit var binding: ActivityMainBinding
    private val listOfSuperheroes = ArrayList<SuperheroImageEntity>()
    private lateinit var superheroAdapter: SuperheroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewSuperheroes.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            superheroAdapter = SuperheroAdapter(listOfSuperheroes) {
                val intent = Intent(this@MainActivity, SuperheroActivity::class.java)
                intent.putExtra(tagId, it.id)
                intent.putExtra(tagUrl, it.url)
                startActivity(intent)
            }
            adapter = superheroAdapter
        }

        lifecycleScope.launch {
            for (i in 1..50) {
                val responseImage = ApiStorage.api.getImage(i.toString())
                if (responseImage.isSuccessful) {
                    val jsonImage = responseImage.body()
                    if (jsonImage != null) {
                        //Log.d("MainActivity", jsonImage.name)
                        listOfSuperheroes.add(jsonImage)
                        superheroAdapter.notifyItemRangeInserted(listOfSuperheroes.size-1, 1)
                    }
                }
            }


        }
    }
}