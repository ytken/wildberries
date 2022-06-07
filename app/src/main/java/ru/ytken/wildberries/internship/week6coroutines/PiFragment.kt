package ru.ytken.wildberries.internship.week6coroutines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.ytken.wildberries.internship.week6coroutines.databinding.FragmentPiBinding

class PiFragment: Fragment(R.layout.fragment_pi) {
    private lateinit var binding: FragmentPiBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPiBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun updateLeibnizTextView(piString: String) {
        binding.textViewPiLeibniz.text = piString
    }

    fun updateChudTextView(piString: String) {
        binding.textViewPiChud.text = piString
    }

}