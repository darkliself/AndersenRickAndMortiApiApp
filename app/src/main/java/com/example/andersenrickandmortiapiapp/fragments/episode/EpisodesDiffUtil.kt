package com.example.andersenrickandmortiapiapp.fragments.episode

import androidx.recyclerview.widget.DiffUtil
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.episodes.EpisodesInfo

//
//import androidx.recyclerview.widget.DiffUtil
//
class EpisodesDiffUtil(
    private val oldList: List<EpisodesInfo>,
    private val newList: List<EpisodesInfo>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser.id == newUser.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser == newUser
    }
}