package com.example.andersenrickandmortiapiapp.fragments.character

import androidx.recyclerview.widget.DiffUtil
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.characters_list.CharacterInfo

//
//import androidx.recyclerview.widget.DiffUtil
//
class CharacterDiffUtil(
    private val oldList: List<CharacterInfo>,
    private val newList: List<CharacterInfo>
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