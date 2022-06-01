package ru.ytken.wildberries.internship.week5ktorserialization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.launch
import ru.ytken.wildberries.internship.week5ktorserialization.databinding.ActivityFavouritesBinding
import ru.ytken.wildberries.internship.week5ktorserialization.databinding.ElementFavouritesBinding
import ru.ytken.wildberries.internship.week5ktorserialization.entities.GetFavouritesEntity

class FavouritesActivity: AppCompatActivity() {
    private lateinit var binding: ActivityFavouritesBinding
    private lateinit var ktorHttpClient: HttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ktorHttpClient = getKtorClient()
        lifecycleScope.launch {
            val listFavourites = ktorHttpClient.get<List<GetFavouritesEntity>>(MainActivity.getOrPostFavouritesUrl) {
                header("x-api-key", MainActivity.apiKey)
            }

            binding.recyclerViewFavourites.apply {
                layoutManager = GridLayoutManager(this@FavouritesActivity, 3)
                adapter = FavouritesAdapter(listFavourites)
            }
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

    override fun onDestroy() {
        ktorHttpClient.close()
        super.onDestroy()
    }
}