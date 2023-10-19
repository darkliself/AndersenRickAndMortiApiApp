package com.example.andersenrickandmortiapiapp.fragments.location.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.andersenrickandmortiapiapp.R
import com.example.andersenrickandmortiapiapp.databinding.LocationItemBinding
import com.example.andersenrickandmortiapiapp.retrofit.models.locatons.LocationInfo


class LocationsAdapter(
) :   RecyclerView.Adapter<LocationsAdapter.LocationListViewHolder>() {
    var locationsList: List<LocationInfo> = emptyList()
        set(newValue) {
            val diffCallback = LocationDiffUtil(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    inner class LocationListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: LocationItemBinding = LocationItemBinding.bind(view)
        val name = binding.name
        val dimension = binding.dimension
        val type = binding.type
        val container = binding.container

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationListViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.location_item, parent, false)
        layout.accessibilityDelegate = View.AccessibilityDelegate()
        return LocationListViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return locationsList.size
    }

    override fun onBindViewHolder(holder: LocationListViewHolder, position: Int) {
        val item = locationsList[position]
        holder.name.text =item.name
        holder.dimension.text = item.dimension
        holder.type.text = item.type
        holder.container.setOnClickListener {
            navigateToContactInfoFragment(it, item.id)
        }
    }

    private fun navigateToContactInfoFragment(view: View, id:Int) {
        val action =
            LocationsFragmentDirections.actionLocationsToLocationDetails(id)
        view.findNavController().navigate(action)
    }

}