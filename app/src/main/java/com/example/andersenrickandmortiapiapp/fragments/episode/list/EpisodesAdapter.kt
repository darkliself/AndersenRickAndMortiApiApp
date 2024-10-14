package com.example.andersenrickandmortiapiapp.fragments.episode.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.andersenrickandmortiapiapp.R
import com.example.andersenrickandmortiapiapp.databinding.EpisodeItemBinding
import com.example.andersenrickandmortiapiapp.fragments.character.details.CharacterDetailsDirections
import com.example.andersenrickandmortiapiapp.fragments.location.details.LocationDetailsDirections
import com.example.andersenrickandmortiapiapp.data.navigation.StartRoute
import com.example.andersenrickandmortiapiapp.retrofit.models.episodes.EpisodesInfo


class EpisodesAdapter(
) : RecyclerView.Adapter<EpisodesAdapter.EpisodeListViewHolder>() {
    var startRoute = StartRoute.EPISODES
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
        val container = binding.container
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
        holder.name.text = item.name
        holder.episode.text = item.episode
        holder.airDate.text = item.airDate
        holder.container.setOnClickListener {
            navigateToEpisodeDetails(it, item.id)
        }
    }

    private fun navigateToEpisodeDetails(view: View, id: Int) {
        val action: NavDirections = when (startRoute) {
            StartRoute.LOCATION_DETAILS ->
                LocationDetailsDirections.actionLocationDetailsToEpisodeDetails(id)

            StartRoute.CHARACTER_DETAILS ->
                CharacterDetailsDirections.actionCharacterDataToEpisodeDetails(id)

            else ->
                EpisodesFragmentDirections.actionEpisodesToEpisodeDetails(id)
        }
        view.findNavController().navigate(action)
    }
}
