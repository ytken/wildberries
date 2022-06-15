package ru.ytken.wildberries.internship.week5okhttpmoshi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import ru.ytken.wildberries.internship.week5okhttpmoshi.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm = ViewModelProvider(this).get(MainViewModel::class.java)
        vm.setFilePath("${filesDir}charactersInfo.txt")

        binding.progressBarLoadCharacters.visibility = View.VISIBLE
        vm.listOfCharacters.observe(this) {
            binding.progressBarLoadCharacters.visibility = View.INVISIBLE
            binding.recyclerViewListCharacters.apply {
                layoutManager = GridLayoutManager(this@MainActivity, 2)
                adapter = CharacterListAdapter(it) {
                    val intent = Intent(this@MainActivity, CharacterActivity::class.java)
                    intent.putExtra(CharacterActivity.tagCharacter, it)
                    startActivity(intent)
                }
            }
        }
    }

}
