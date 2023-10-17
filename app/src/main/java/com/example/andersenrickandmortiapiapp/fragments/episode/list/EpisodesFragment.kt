package com.example.andersenrickandmortiapiapp.fragments.episode.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.andersenrickandmortiapiapp.databinding.FragmentEpisodesBinding
import com.example.andersenrickandmortiapiapp.fragments.BaseFragment
import com.example.andersenrickandmortiapiapp.utils.ConnectivityStatus
import kotlinx.coroutines.launch


class EpisodesFragment : BaseFragment() {
    private var _binding: FragmentEpisodesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EpisodesViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodesBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isConnected = isNetworkConnected(requireContext())
        viewModel.initEpisodesList()
        recyclerView = binding.episodesItemRecyclerView
//        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = EpisodesAdapter()

        recyclerView.apply {
//            layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = adapter
            recyclerView.addOnScrollListener(this@EpisodesFragment.scrollListener)
        }

        lifecycleScope.launch {
            viewModel.episodes.collect {
                adapter.episodesList = it
            }
        }
        binding.swiperefresh.setOnRefreshListener {
            viewModel.isAllowedPagination = true
            viewModel.isConnected = isNetworkConnected(requireContext())
            viewModel.initEpisodesList()
            binding.swiperefresh.isRefreshing = false
        }
    }



    var isScrolling = false
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!recyclerView.canScrollVertically(1) && dy > 0) {
                viewModel.isConnected = isNetworkConnected(requireContext())
                viewModel.loadNextPage()
            } else if (!recyclerView.canScrollVertically(-1) && dy < 0) {

            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

}