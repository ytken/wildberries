package ru.ytken.wildberries.internship.week5retrofitgson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import ru.ytken.wildberries.internship.week5retrofitgson.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    companion object {
        const val tagSuperhero = "TAG_SUPERHERO"
    }
    private lateinit var binding: FragmentMainBinding
    private lateinit var superheroAdapter: SuperheroAdapter
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

        vm.listOfSuperheroes.observe(viewLifecycleOwner) {
            binding.recyclerViewSuperheroes.apply {
                layoutManager = GridLayoutManager(context, 2)
                superheroAdapter = SuperheroAdapter(it) {
                    vm.presenter.openHeroScreen(it)
                }
                adapter = superheroAdapter
            }
        }

        binding.imageViewInfoAbout.setOnClickListener {
            vm.presenter.openAboutScreen()
        }
    }
}