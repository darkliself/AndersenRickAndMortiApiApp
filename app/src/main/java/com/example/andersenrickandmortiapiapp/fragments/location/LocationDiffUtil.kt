package com.example.andersenrickandmortiapiapp.fragments.location

import androidx.recyclerview.widget.DiffUtil
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.locatons.LocationInfo

//
//import androidx.recyclerview.widget.DiffUtil
//
class LocationDiffUtil(
    private val oldList: List<LocationInfo>,
    private val newList: List<LocationInfo>
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