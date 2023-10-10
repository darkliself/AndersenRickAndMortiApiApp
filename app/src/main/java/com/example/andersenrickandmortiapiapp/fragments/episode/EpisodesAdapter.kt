package com.example.andersenrickandmortiapiapp.fragments.episode

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.andersenrickandmortiapiapp.R
import com.example.andersenrickandmortiapiapp.databinding.EpisodeItemBinding
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.episodes.EpisodesInfo


class EpisodesAdapter(
) :   RecyclerView.Adapter<EpisodesAdapter.EpisodeListViewHolder>() {

    var episodesList: List<EpisodesInfo> = emptyList()
        set(newValue) {
            val diffCallback = EpisodesDiffUtil(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    inner class EpisodeListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: EpisodeItemBinding = EpisodeItemBinding.bind(view)
        val name = binding.name
        val episode = binding.episode
        val airDate = binding.airDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeListViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.episode_item, parent, false)
        layout.accessibilityDelegate = View.AccessibilityDelegate()
        return EpisodeListViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return episodesList.size
    }

    override fun onBindViewHolder(holder: EpisodeListViewHolder, position: Int) {
        val item = episodesList[position]
        holder.name.text =item.name
        holder.episode.text = item.episode
        holder.airDate.text = item.airDate

    }
//
//    fun search(query: String) {
//        val newList =
//            ContactsRepo.contactsList.filter { it.name.lowercase().contains(query.lowercase()) }
//        contactsList = newList
//    }


//    private fun navigateToContactInfoFragment(view: View) {
////        val action =
////            ContactListFragmentDirections.actionContactListFragmentToContactInfoFragment()
////        view.findNavController().navigate(action)
////    }

//    private fun showToast(message: String) {
//        Toast.makeText(
//            getContext,
//            message,
//            Toast.LENGTH_LONG
//        ).show()
//    }
}