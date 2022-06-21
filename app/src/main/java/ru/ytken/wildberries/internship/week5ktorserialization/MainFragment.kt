package ru.ytken.wildberries.internship.week5ktorserialization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.ytken.wildberries.internship.week5ktorserialization.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private val vm: MainViewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    //private val vm = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(MainViewModel::class.java)
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.currentCat.observe(viewLifecycleOwner) {
            binding.draweeViewCat.setImageURI(it.url)
        }

        binding.imageButtonCross.setOnClickListener {
            vm.dislikeCurrentCat()
        }

        binding.imageButtonHeart.setOnClickListener {
            Toast.makeText(context, "Добавлен в избранные", Toast.LENGTH_SHORT).show()
            vm.saveCurrentCatToFavourites()
            vm.likeCurrentCat()
        }

        binding.imageButtonFavourites.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_favouritesFragment)
        }

        binding.imageViewInfoAbout.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_aboutFragment)
        }
    }
}