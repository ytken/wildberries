package ru.ytken.wildberries.internship.week5okhttpmoshi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import ru.ytken.wildberries.internship.week5okhttpmoshi.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    private val vm: MainViewModel by activityViewModels()

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
        vm.setFilePath("${activity?.filesDir}charactersInfo.txt")

        binding.imageViewInfoAbout.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, AboutFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.progressBarLoadCharacters.visibility = View.VISIBLE
        vm.listOfCharacters.observe(requireActivity()) {
            binding.progressBarLoadCharacters.visibility = View.INVISIBLE
            binding.recyclerViewListCharacters.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = CharacterListAdapter(it) {
                    val childFragment = CharacterFragment()
                    childFragment.arguments = Bundle().apply {
                        putSerializable(CharacterFragment.tagCharacter, it)
                        Log.d("shareModel", "${CharacterFragment.tagCharacter} $it")
                    }
                    Log.d("shareModel", (childFragment.arguments != null).toString())
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, childFragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }
}
