package ru.ytken.wildberries.internship.week5retrofitgson

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import ru.ytken.wildberries.internship.week5retrofitgson.databinding.ActivitySuperheroBinding
import ru.ytken.wildberries.internship.week5retrofitgson.models.SuperheroEntity

class SuperheroActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySuperheroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuperheroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val superhero = intent.extras?.getSerializable(MainActivity.tagSuperhero) as SuperheroEntity

        Picasso.with(this@SuperheroActivity)
            .load(superhero.images.lg)
            .into(binding.imageView)

        binding.textViewGender.text = superhero.appearance.gender
        binding.textViewRace.text = superhero.appearance.race
        binding.textViewHeight.text = superhero.appearance.height[0]
        binding.textViewWeight.text = superhero.appearance.weight[0]
        binding.textViewEyeColor.text = superhero.appearance.eyeColor
        binding.textViewHairColor.text = superhero.appearance.hairColor
    }

}