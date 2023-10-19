package com.example.andersenrickandmortiapiapp.fragments.episode.list.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.andersenrickandmortiapiapp.databinding.FragmentEpisodesSearchBinding
import com.example.andersenrickandmortiapiapp.fragments.BaseFragment
import com.example.andersenrickandmortiapiapp.fragments.episode.list.EpisodesViewModel

class EpisodeSearchFragment : BaseFragment() {
    private var _binding: FragmentEpisodesSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EpisodesViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodesSearchBinding.inflate(inflater, container, false)
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
        binding.nameSearch.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.filtersList.visibility = View.VISIBLE
            }
        }
    }

    private fun resetFilters() {
        binding.nameSearch.setText("")
        binding.episodeSearch.setText("")
        binding.filtersList.visibility = View.GONE
        binding.nameSearch.clearFocus()
        closeKeyboard()
    }

    private fun sendSearchRequest() {
        val nameQuery = binding.nameSearch.text
        val episodeQuery = binding.episodeSearch.text
        viewModel.isConnected = isNetworkConnected(requireContext())
        viewModel.search(
            name = nameQuery.toString(),
            episode = episodeQuery.toString(),
            requireContext()
        )
        binding.filtersList.visibility = View.GONE
        binding.nameSearch.clearFocus()
        closeKeyboard()
    }
}