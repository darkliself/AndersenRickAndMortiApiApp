package com.example.andersenrickandmortiapiapp.fragments.episode.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.andersenrickandmortiapiapp.databinding.FragmentEpisodeDetailsBinding
import com.example.andersenrickandmortiapiapp.fragments.BaseFragment
import com.example.andersenrickandmortiapiapp.fragments.character.list.CharacterAdapter
import com.example.andersenrickandmortiapiapp.data.navigation.StartRoute
import kotlinx.coroutines.launch


private const val ARG_PARAM1 = "id"

class EpisodeDetails : BaseFragment() {
    private var id: Int = 0
    private lateinit var recyclerView: RecyclerView
    private var _binding: FragmentEpisodeDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EpisodeDetailsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ARG_PARAM1)
        }
        showNoInternetToast()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isConnected = isNetworkConnected(requireContext())
        recyclerView = binding.characterItemRecyclerView
        val adapter = CharacterAdapter()
        adapter.startRoute = StartRoute.EPISODE_DETAILS
        recyclerView.apply {
            recyclerView.adapter = adapter
        }
        addDecoration(recyclerView)
        viewModel.getEpisodeDetails(id)

        lifecycleScope.launch {
            viewModel.episode.collect { data ->
                if (data != null) {
                    binding.name.text = data.name
                    binding.airDate.text = data.airDate
                    binding.episode.text = data.episode
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
