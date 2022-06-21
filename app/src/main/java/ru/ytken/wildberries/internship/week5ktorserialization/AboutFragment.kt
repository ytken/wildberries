package ru.ytken.wildberries.internship.week5ktorserialization

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.ytken.wildberries.internship.week5ktorserialization.databinding.FragmentAboutBinding

class AboutFragment: Fragment() {
    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageViewCopyLink.setOnClickListener {
            val clipboardManager = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("label", "https://github.com/ytken/wildberries/tree/week_8_3")
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(context, "Ссылка скопирована", Toast.LENGTH_SHORT).show()
        }
    }

}