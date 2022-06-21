package ru.ytken.wildberries.internship.week5retrofitgson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.squareup.picasso.Picasso
import ru.ytken.wildberries.internship.week5retrofitgson.databinding.FragmentSuperheroBinding
import ru.ytken.wildberries.internship.week5retrofitgson.models.SuperheroEntity

class SuperheroFragment: Fragment() {
    private lateinit var binding: FragmentSuperheroBinding
    private val vm: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuperheroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val superhero = arguments?.getSerializable(MainFragment.tagSuperhero) as SuperheroEntity?
        if (superhero != null) {
            Picasso.with(context)
                .load(superhero.images.lg)
                .into(binding.imageView)

            binding.textViewGender.text = superhero.appearance.gender
            binding.textViewRace.text = superhero.appearance.race
            binding.textViewHeight.text = superhero.appearance.height[0]
            binding.textViewWeight.text = superhero.appearance.weight[0]
            binding.textViewEyeColor.text = superhero.appearance.eyeColor
            binding.textViewHairColor.text = superhero.appearance.hairColor
        } else
            Toast.makeText(context, "Ошибка передачи данных", Toast.LENGTH_SHORT)
                .show()
    }

}