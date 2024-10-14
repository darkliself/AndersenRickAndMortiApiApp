package com.example.andersenrickandmortiapiapp.fragments.character.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.andersenrickandmortiapiapp.R
import com.example.andersenrickandmortiapiapp.databinding.FragmentCharacterDetailsBinding
import com.example.andersenrickandmortiapiapp.fragments.BaseFragment
import com.example.andersenrickandmortiapiapp.fragments.episode.list.EpisodesAdapter
import com.example.andersenrickandmortiapiapp.data.navigation.StartRoute
import kotlinx.coroutines.launch

private const val ARG_CHARACTER_ID = "id"

class CharacterDetails : BaseFragment() {

    private var id: Int = 0
    private lateinit var recyclerView: RecyclerView
    private val viewModel: CharacterDetailsViewModel by activityViewModels()
    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ARG_CHARACTER_ID)
        }
        showNoInternetToast()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        }
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        viewModel.getCharacterDetails(id)
        lifecycleScope.launch {
            viewModel.character.collect { data ->
                if (data != null) {
                    binding.name.text = data.name
                    binding.status.text = data.status
                    binding.species.text = data.species
                    binding.gender.text = data.gender
                    binding.origin.text = data.origin.name
                    binding.origin.setOnClickListener {
                        navigateToLocationDetails(it, data.origin.url.split("/").last().toInt())
                    }
                    binding.location.text = data.location.name
                    binding.location.setOnClickListener {
                        navigateToLocationDetails(it, data.location.url.split("/").last().toInt())
                    }
                    binding.imageView.load(data.image) {
                        placeholder(R.drawable.placeholder)
                        error(R.drawable.placeholder)
                        build()
                    }
                    binding.created.text = data.created
                    viewModel.episode.collect {
                        adapter.episodesList = it
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearData()
    }

    private fun navigateToLocationDetails(view: View, id: Int) {
        val action: NavDirections =
            CharacterDetailsDirections.actionCharacterDataToLocationDetails(id)
        view.findNavController().navigate(action)
    }


}