package com.example.andersenrickandmortiapiapp.fragments.character.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.andersenrickandmortiapiapp.R
import com.example.andersenrickandmortiapiapp.databinding.CharacterItemBinding
import com.example.andersenrickandmortiapiapp.fragments.episode.details.EpisodeDetailsDirections
import com.example.andersenrickandmortiapiapp.fragments.location.details.LocationDetailsDirections
import com.example.andersenrickandmortiapiapp.data.navigation.StartRoute
import com.example.andersenrickandmortiapiapp.retrofit.models.characters.CharacterInfo


class CharacterAdapter(
) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {
    var startRoute = StartRoute.CHARACTERS
    var charactersList: List<CharacterInfo> = emptyList()
        set(newValue) {
            val diffCallback = CharacterDiffUtil(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    inner class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: CharacterItemBinding = CharacterItemBinding.bind(view)
        val name = binding.name
        val species = binding.species
        val status = binding.status
        val gender = binding.gender
        val image = binding.image
        val container  = binding.container
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.character_item, parent, false)
        layout.accessibilityDelegate = View.AccessibilityDelegate()
        return CharacterViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return charactersList.size
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = charactersList[position]
        holder.name.text = item.name
        holder.species.text = item.species
        holder.gender.text = item.gender
        holder.status.text = item.status
        holder.image.load(item.image) {
            placeholder(R.drawable.placeholder)
            error(R.drawable.placeholder)
            build()
        }
        holder.container.setOnClickListener {
            navigateToCharacterDetails(it, item.id)
        }

    }

    private fun navigateToCharacterDetails(view: View, id: Int) {
        val action: NavDirections = when (startRoute) {
            StartRoute.CHARACTERS ->
                CharacterFragmentDirections.actionCharacterToCharacterData(id)
            StartRoute.LOCATION_DETAILS -> LocationDetailsDirections.actionLocationDetailsToCharacterData(id)
            else -> EpisodeDetailsDirections.actionEpisodeDetailsToCharacterData(id)
        }
        view.findNavController().navigate(action)
    }
}

