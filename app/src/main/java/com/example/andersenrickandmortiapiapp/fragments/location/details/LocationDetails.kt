package com.example.andersenrickandmortiapiapp.fragments.location.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.andersenrickandmortiapiapp.databinding.FragmentLocationDetailsBinding
import com.example.andersenrickandmortiapiapp.fragments.character.list.CharacterAdapter
import com.example.andersenrickandmortiapiapp.fragments.episode.details.EpisodeDetailsViewModel
import com.example.andersenrickandmortiapiapp.navigation_data.StartRoute
import kotlinx.coroutines.launch


private const val ARG_ID = "id"

class LocationDetails : Fragment() {
    private var _binding: FragmentLocationDetailsBinding? = null
    private val binding get() = _binding!!
    private var value = 0
    private val viewModel: LocationDetailsViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            value = it.getInt(ARG_ID)
        }
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
        recyclerView = binding.characterItemRecyclerView
        val adapter = CharacterAdapter()
        adapter.startRoute = StartRoute.LOCATION_DETAILS
        recyclerView.apply {
            recyclerView.adapter = adapter
        }

        if (value != null) {
            Log.d("CHARACTER_DATA", value.toString())
            viewModel.getLocationDetails(
                id = value,
                isConnected = true
            )
        } else {
            Log.d("CHARACTER_DATA", "no data")
        }

        lifecycleScope.launch {
            viewModel.location.collect { data ->
                if (data != null) {
                    binding.name.text = data.name
                    binding.type.text = data.type
                    binding.dimension.text = data.dimension
                    binding.url.text = data.url
                    binding.created.text = data.created

                    viewModel.characters.collect { list ->
                        adapter.charactersList = list
                    }
                }
            }
        }
    }
}

