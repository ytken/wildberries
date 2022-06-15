package ru.ytken.wildberries.internship.week5ktorserialization

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.ytken.wildberries.internship.week5ktorserialization.databinding.ActivityFavouritesBinding
import ru.ytken.wildberries.internship.week5ktorserialization.databinding.ElementFavouritesBinding
import ru.ytken.wildberries.internship.week5ktorserialization.entities.CatEntity
import ru.ytken.wildberries.internship.week5ktorserialization.entities.GetFavouritesEntity

class FavouritesActivity: AppCompatActivity() {
    private lateinit var binding: ActivityFavouritesBinding
    private val vm: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm.getListOfFavourites()

        vm.listOfFavouritesRoom.observe(this) {
            Log.d("Favourites", it.isEmpty().toString())
            if (it.isEmpty())
                vm.getListOfFavouritesFromBackend()
            else
                initRecyclerView(it.map {
                    GetFavouritesEntity(
                        0,
                        CatEntity(it.id, it.image)
                    )
                })
        }

        vm.listOfFavouritesBackend.observe(this) {
            initRecyclerView(it)
        }

    }

    fun initRecyclerView(listGetFavouritesEntity: List<GetFavouritesEntity>) {
        binding.recyclerViewFavourites.apply {
            layoutManager = GridLayoutManager(this@FavouritesActivity, 3)
            adapter = FavouritesActivity.FavouritesAdapter(listGetFavouritesEntity)
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