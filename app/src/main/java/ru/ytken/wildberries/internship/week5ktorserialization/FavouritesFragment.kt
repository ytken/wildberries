package ru.ytken.wildberries.internship.week5ktorserialization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.ytken.wildberries.internship.week5ktorserialization.databinding.ElementFavouritesBinding
import ru.ytken.wildberries.internship.week5ktorserialization.databinding.FragmentFavouritesBinding
import ru.ytken.wildberries.internship.week5ktorserialization.entities.GetFavouritesEntity

class FavouritesFragment: Fragment() {
    private lateinit var binding: FragmentFavouritesBinding
    private val vm: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.getListOfFavourites()

        vm.listOfFavouriteCats.observe(viewLifecycleOwner) {
            initRecyclerView(it)
        }
    }

    fun initRecyclerView(listGetFavouritesEntity: List<GetFavouritesEntity>) {
        binding.recyclerViewFavourites.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = FavouritesFragment.FavouritesAdapter(listGetFavouritesEntity)
        }
    }

    class FavouritesAdapter(val listFavourites: List<GetFavouritesEntity>):
        RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder>() {
        class FavouritesViewHolder(val binding: ElementFavouritesBinding): RecyclerView.ViewHolder(binding.root) {
            fun bind(entity: GetFavouritesEntity) {
                binding.draweeElementFavourite.setImageURI(entity.image.url)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
            val itemBinding = ElementFavouritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return FavouritesViewHolder(itemBinding)
        }

        override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
            holder.bind(listFavourites[position])
        }

        override fun getItemCount(): Int = listFavourites.size
    }


}