package ru.ytken.wildberries.internship.week5retrofitgson

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.ytken.wildberries.internship.week5retrofitgson.databinding.RowSuperheroBinding

class SuperheroAdapter(
    private val listSuperheroes: List<SuperheroImageEntity>,
    private val clickAction: (superhero: SuperheroImageEntity) -> Unit
): RecyclerView.Adapter<SuperheroAdapter.SuperheroViewHolder>() {

    class SuperheroViewHolder(val context: Context, val binding: RowSuperheroBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(superhero: SuperheroImageEntity) {
            binding.textViewNameHero.text = superhero.name
            Picasso.with(context)
                .load(superhero.url)
                .transform(CircularTransformation())
                .into(binding.imageViewImageHero)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperheroViewHolder {
        val rowBinding = RowSuperheroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SuperheroViewHolder(parent.context, rowBinding)
    }

    override fun onBindViewHolder(holder: SuperheroViewHolder, position: Int) {
        holder.bind(listSuperheroes[position])

        holder.itemView.setOnClickListener {
            clickAction(listSuperheroes[position])
        }
    }

    override fun getItemCount(): Int = listSuperheroes.size

}