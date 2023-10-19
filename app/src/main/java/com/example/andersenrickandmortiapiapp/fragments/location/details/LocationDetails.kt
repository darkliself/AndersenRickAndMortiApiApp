package com.example.andersenrickandmortiapiapp.fragments.location.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.andersenrickandmortiapiapp.databinding.FragmentLocationDetailsBinding
import com.example.andersenrickandmortiapiapp.fragments.BaseFragment
import com.example.andersenrickandmortiapiapp.fragments.character.list.CharacterAdapter
import com.example.andersenrickandmortiapiapp.navigation_data.StartRoute
import kotlinx.coroutines.launch


private const val ARG_ID = "id"

class LocationDetails : BaseFragment() {
    private var _binding: FragmentLocationDetailsBinding? = null
    private val binding get() = _binding!!
    private var id = 0
    private val viewModel: LocationDetailsViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ARG_ID)
        }
        showNoInternetToast()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isConnected = isNetworkConnected(requireContext())
        recyclerView = binding.characterItemRecyclerView
        val adapter = CharacterAdapter()
        adapter.startRoute = StartRoute.LOCATION_DETAILS
        recyclerView.apply {
            recyclerView.adapter = adapter
        }
        addDecoration(recyclerView)
        viewModel.getLocationDetails(id)

        lifecycleScope.launch {
            viewModel.location.collect { data ->
                if (data != null) {
                    binding.name.text = data.name
                    binding.type.text = data.type
                    binding.dimension.text = data.dimension
                    binding.created.text = data.created
                    viewModel.characters.collect { list ->
                        adapter.charactersList = list
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearData()
    }
}

