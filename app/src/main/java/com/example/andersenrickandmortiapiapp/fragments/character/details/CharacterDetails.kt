package com.example.andersenrickandmortiapiapp.fragments.character.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.andersenrickandmortiapiapp.databinding.FragmentCharacterDetailsBinding
import com.example.andersenrickandmortiapiapp.fragments.BaseFragment
import com.example.andersenrickandmortiapiapp.fragments.character.list.CharacterFragmentDirections
import com.example.andersenrickandmortiapiapp.fragments.episode.details.EpisodeDetailsDirections
import com.example.andersenrickandmortiapiapp.fragments.episode.list.EpisodesAdapter
import com.example.andersenrickandmortiapiapp.fragments.location.details.LocationDetailsDirections
import com.example.andersenrickandmortiapiapp.navigation_data.StartRoute
import kotlinx.coroutines.launch

private const val ARG_CHARACTER_ID = "id"

class CharacterDetails : BaseFragment() {

    private var value: Int? = null
    private lateinit var recyclerView: RecyclerView
    private val viewModel: CharacterDetailsViewModel by activityViewModels()
    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            value = it.getInt(ARG_CHARACTER_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isConnected = isNetworkConnected(requireContext())
        recyclerView = binding.locationsRecyclerView
        val adapter = EpisodesAdapter()
        adapter.startRoute = StartRoute.CHARACTER_DETAILS
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = adapter
//            recyclerView.addOnScrollListener(this@CharacterFragment.scrollListener)
        }


        if (value != null) {
            Log.d("CHARACTER_DATA", value.toString())
            viewModel.loadSingleCharacter(value!!)
        } else {
            Log.d("CHARACTER_DATA", "no data")
        }
        lifecycleScope.launch {
            viewModel.character.collect { data ->
                if (data != null) {
                    binding.name.text = data.name
                    binding.status.text = data.status
                    binding.species.text = data.species
//                    binding.type.text = data.type
                    binding.gender.text = data.gender
                    binding.origin.text = data.origin.name
                    binding.origin.setOnClickListener {
                        navigateToLocationDetails(it, data.origin.url.split("/").last().toInt())
                    }
                    binding.location.text = data.location.name
                    binding.location.setOnClickListener {
                        navigateToLocationDetails(it, data.location.url.split("/").last().toInt())
                    }
                    binding.imageView.load(data.image)
                    binding.created.text = data.created
                    viewModel.episode.collect {
                        adapter.episodesList = it
                    }

                }
            }
        }
    }

    private fun navigateToLocationDetails(view: View, id: Int) {
        val action: NavDirections =
            CharacterDetailsDirections.actionCharacterDataToLocationDetails(id)
        view.findNavController().navigate(action)
    }

}