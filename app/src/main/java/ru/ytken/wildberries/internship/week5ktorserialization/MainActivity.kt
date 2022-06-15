package ru.ytken.wildberries.internship.week5ktorserialization

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.ytken.wildberries.internship.week5ktorserialization.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val vm: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm.currentCat.observe(this) {
            binding.draweeViewCat.setImageURI(it.url)
        }

        binding.imageButtonCross.setOnClickListener {
            vm.dislikeCurrentCat()
        }

        binding.imageButtonHeart.setOnClickListener {
            Toast.makeText(this@MainActivity, "Добавлен в избранные", Toast.LENGTH_SHORT).show()
            vm.saveCurrentCatToFavourites()
            vm.likeCurrentCat()
        }

        binding.imageButtonFavourites.setOnClickListener {
            startActivity(Intent(this, FavouritesActivity::class.java))
        }
    }
}