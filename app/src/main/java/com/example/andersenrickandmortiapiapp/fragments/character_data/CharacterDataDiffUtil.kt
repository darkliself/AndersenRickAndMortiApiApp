package com.example.andersenrickandmortiapiapp.fragments.character_data

import androidx.recyclerview.widget.DiffUtil
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.characters_list.CharacterInfo

class CharacterDataDiffUtil(
    private val oldList: List<String>,
    private val newList: List<String>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}