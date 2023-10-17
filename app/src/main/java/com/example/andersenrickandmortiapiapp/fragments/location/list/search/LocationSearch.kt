package com.example.andersenrickandmortiapiapp.fragments.location.list.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.andersenrickandmortiapiapp.databinding.FragmentLocationsSearchBinding
import com.example.andersenrickandmortiapiapp.fragments.BaseFragment
import com.example.andersenrickandmortiapiapp.fragments.location.list.LocationViewModel


class LocationSearch : BaseFragment() {

    private var _binding: FragmentLocationsSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LocationViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationsSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submitButton.setOnClickListener {
            sendSearchRequest()
        }
        binding.resetFilters.setOnClickListener {
            resetFilters()
        }
        binding.nameSearch.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                binding.filtersList.visibility = View.VISIBLE
            }
        }
    }

    private fun resetFilters() {
        binding.nameSearch.setText("")
        binding.typeSearch.setText("")
        binding.dimensionSearch.setText("")
        binding.filtersList.visibility = View.GONE
        binding.nameSearch.clearFocus()
        closeKeyboard()
    }

    private fun sendSearchRequest() {
        val nameQuery = binding.nameSearch.text
        val typeQuery = binding.typeSearch.text
        val dimensionQuery = binding.dimensionSearch.text
        viewModel.isConnected = isNetworkConnected(requireContext())
        viewModel.search(
            name = nameQuery.toString(),
            type = typeQuery.toString(),
            dimension = dimensionQuery.toString()
        )
        binding.filtersList.visibility = View.GONE
        binding.nameSearch.clearFocus()
        closeKeyboard()
    }
}