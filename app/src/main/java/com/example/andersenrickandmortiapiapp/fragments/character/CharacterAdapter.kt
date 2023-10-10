package com.example.andersenrickandmortiapiapp.fragments.character

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.andersenrickandmortiapiapp.R
import com.example.andersenrickandmortiapiapp.databinding.CharacterItemBinding
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.characters_list.CharacterInfo


class CharacterAdapter(
) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {
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
        holder.name.setOnClickListener {
            navigateToCharacterDataFragment(it, item.id)
        }
    }
//
//    fun search(query: String) {
//        val newList =
//            ContactsRepo.contactsList.filter { it.name.lowercase().contains(query.lowercase()) }
//        contactsList = newList
//    }


    private fun navigateToCharacterDataFragment(view: View, id: Int) {
        val action = CharacterFragmentDirections.actionCharacterToCharacterData(id)
        view.findNavController().navigate(action)
    }


//    private fun showToast(message: String) {
//        Toast.makeText(
//            getContext,
//            message,
//            Toast.LENGTH_LONG
//        ).show()
//    }
}