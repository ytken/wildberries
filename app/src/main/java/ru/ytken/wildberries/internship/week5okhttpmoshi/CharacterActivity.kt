package ru.ytken.wildberries.internship.week5okhttpmoshi

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import ru.ytken.wildberries.internship.week5okhttpmoshi.databinding.ActivityCharacterBinding
import ru.ytken.wildberries.internship.week5okhttpmoshi.databinding.ActivityMainBinding

class CharacterActivity: AppCompatActivity() {
    companion object {
        val tagCharacter = "TAG_CHARACTER"
    }
    private lateinit var binding: ActivityCharacterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val character = intent.getSerializableExtra(tagCharacter) as CharacterModel
        if (character != null) {
            binding.imageViewCharacterImage.load(MainActivity.url + character.imgUrl)
            binding.textViewCharacterName.text = character.localizedName
            if (character.roles?.isNotEmpty() == true)
                for (role in character.roles) {
                    val textView = TextView(this)
                    textView.text = role
                    textView.gravity = Gravity.CENTER
                    binding.linearLayoutRoles.addView(textView)
                }
            binding.textViewAttackMax.text = character.baseAttackMax.toString()
            binding.textViewAttackMin.text = character.baseAttackMin.toString()
            binding.textViewBaseHealth.text = character.baseHealth.toString()
            binding.textViewBaseMana.text = character.baseMana.toString()
        } else
            Toast.makeText(this, "Ошибка передачи данных", Toast.LENGTH_SHORT).show()
    }
}