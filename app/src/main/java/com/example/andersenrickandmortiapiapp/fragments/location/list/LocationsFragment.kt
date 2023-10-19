package com.example.andersenrickandmortiapiapp.fragments.location.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.andersenrickandmortiapiapp.databinding.FragmentLocationsBinding
import com.example.andersenrickandmortiapiapp.fragments.BaseFragment
import kotlinx.coroutines.launch


class LocationsFragment : BaseFragment() {
    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LocationViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.isConnected = isNetworkConnected(requireContext())
        if (viewModel.location.value.isEmpty()) {
            viewModel.getData()
        }
        showNoInternetToast()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.locationItemRecyclerView
        val adapter = LocationsAdapter()

        recyclerView.apply {
            recyclerView.adapter = adapter
            recyclerView.addOnScrollListener(this@LocationsFragment.scrollListener)
        }
        addDecoration(recyclerView)

        lifecycleScope.launch {
            viewModel.location.collect {
                adapter.locationsList = it
            }
        }

        binding.swiperefresh.setOnRefreshListener {
            viewModel.isConnected = isNetworkConnected(requireContext())
            viewModel.refreshState()
            binding.swiperefresh.isRefreshing = false
        }
        lifecycleScope.launch {
            viewModel.isLoading.collect {
                binding.loadingIndicator.visibility = it
            }
        }
    }
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!recyclerView.canScrollVertically(1) && dy > 0) {
                viewModel.isConnected = isNetworkConnected(requireContext())
                viewModel.getData()
            }
        }
    }
}