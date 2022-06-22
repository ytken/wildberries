package ru.ytken.wildberries.internship.week5okhttpmoshi

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.ytken.wildberries.internship.week5okhttpmoshi.databinding.RowCharacterBinding

class CharacterListAdapter(
    private val characters: List<CharacterModel>,
    private val clickAction: (character: CharacterModel) -> Unit
): RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val itemBinding = RowCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position])

        holder.itemView.setOnClickListener {
            clickAction(characters[position])
        }
    }

    override fun getItemCount(): Int = characters.size

    class CharacterViewHolder(val binding: RowCharacterBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(character: CharacterModel) {
            binding.textViewNameChar.text = character.localizedName
            binding.textViewAttackType.text = character.attackType
            val imageUrl = Repository.url + character.iconUrl
            binding.imageViewNameChar.load(imageUrl)
        }
    }
}

