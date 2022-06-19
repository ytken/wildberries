package ru.ytken.wildberries.internship.week5okhttpmoshi

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import coil.load
import ru.ytken.wildberries.internship.week5okhttpmoshi.databinding.FragmentCharacterBinding

class CharacterFragment: Fragment(R.layout.fragment_character) {
    companion object {
        val tagCharacter = "TAG_CHARACTER"
    }
    private lateinit var binding: FragmentCharacterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val character = arguments?.getSerializable(tagCharacter)
        Log.d("shareModel", "$tagCharacter $character")
        if (character != null) {
            val mcharacter = character as CharacterModel
            binding.imageViewCharacterImage.load(MainViewModel.url + mcharacter.imgUrl)
            binding.textViewCharacterName.text = mcharacter.localizedName
            if (mcharacter.roles?.isNotEmpty() == true)
                for (role in mcharacter.roles) {
                    val textView = TextView(context)
                    textView.text = role
                    textView.gravity = Gravity.CENTER
                    binding.linearLayoutRoles.addView(textView)
                }
            binding.textViewAttackMax.text = mcharacter.baseAttackMax.toString()
            binding.textViewAttackMin.text = mcharacter.baseAttackMin.toString()
            binding.textViewBaseHealth.text = mcharacter.baseHealth.toString()
            binding.textViewBaseMana.text = mcharacter.baseMana.toString()
        } else
            Toast.makeText(context, "Ошибка передачи данных", Toast.LENGTH_SHORT).show()
    }
}