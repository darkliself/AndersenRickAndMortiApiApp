package com.example.andersenrickandmortiapiapp.fragments.location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.andersenrickandmortiapiapp.R
import com.example.andersenrickandmortiapiapp.databinding.LocationItemBinding
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.locatons.LocationInfo


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