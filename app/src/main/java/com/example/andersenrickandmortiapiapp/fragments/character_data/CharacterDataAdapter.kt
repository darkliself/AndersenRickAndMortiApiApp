package com.example.andersenrickandmortiapiapp.fragments.character_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.andersenrickandmortiapiapp.R
import com.example.andersenrickandmortiapiapp.databinding.SingleStringItemBinding


class CharacterDataAdapter(
) :
    RecyclerView.Adapter<CharacterDataAdapter.CharacterDataViewHolder>() {
    var episodesList: List<String> = emptyList()
        set(newValue) {
            val diffCallback = CharacterDataDiffUtil(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }


    inner class CharacterDataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: SingleStringItemBinding = SingleStringItemBinding.bind(view)
        val episode = binding.textViewItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterDataViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.single_string_item, parent, false)
        layout.accessibilityDelegate = View.AccessibilityDelegate()
        return CharacterDataViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return episodesList.size
    }

    override fun onBindViewHolder(holder: CharacterDataViewHolder, position: Int) {
        val item = episodesList[position]
        holder.episode.text = item
    }

//        holder.name.setOnClickListener {
//            navigateToCharacterDataFragment(it, item.id)
//        }
}


//    private fun navigateToCharacterDataFragment(view: View, id: Int) {
//        val action = CharacterFragmentDirections.actionCharacterToCharacterData(id)
//        view.findNavController().navigate(action)
//    }


//    private fun showToast(message: String) {
//        Toast.makeText(
//            getContext,
//            message,
//            Toast.LENGTH_LONG
//        ).show()
//    }
