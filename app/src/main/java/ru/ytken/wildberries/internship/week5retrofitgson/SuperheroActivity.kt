package ru.ytken.wildberries.internship.week5retrofitgson

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import ru.ytken.wildberries.internship.week5retrofitgson.databinding.ActivitySuperheroBinding

class SuperheroActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySuperheroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuperheroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val idSuperhero = intent.extras?.getString(MainActivity.tagId)
        val urlSuperhero = intent.extras?.getString(MainActivity.tagUrl)
        binding.progressBar.visibility = View.VISIBLE

        if (idSuperhero != null)
            lifecycleScope.launch {
                val responseAppearance = ApiStorage.api.getAppearance(idSuperhero)
                if (responseAppearance.isSuccessful) {
                    val superheroAppearance = responseAppearance.body()

                    // Картинка закэширована Picasso
                    Picasso.with(this@SuperheroActivity)
                        .load(urlSuperhero)
                        .into(binding.imageView)

                    binding.textViewGender.text = superheroAppearance?.gender
                    binding.textViewRace.text = superheroAppearance?.race
                    binding.textViewHeight.text = superheroAppearance?.height?.get(1) ?: ""
                    binding.textViewWeight.text = superheroAppearance?.weight?.get(1) ?: ""
                    binding.textViewEyeColor.text = superheroAppearance?.eyeColor
                    binding.textViewHairColor.text = superheroAppearance?.hairColor
                }
                binding.progressBar.visibility = View.GONE
            }
    }

}