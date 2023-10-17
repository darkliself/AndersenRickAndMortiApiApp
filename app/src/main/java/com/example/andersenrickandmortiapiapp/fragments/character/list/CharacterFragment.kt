package com.example.andersenrickandmortiapiapp.fragments.character.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.andersenrickandmortiapiapp.databinding.FragmentCharactersBinding
import com.example.andersenrickandmortiapiapp.fragments.BaseFragment
import kotlinx.coroutines.launch


class CharacterFragment : BaseFragment() {
    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharacterViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isConnected = isNetworkConnected(requireContext())
        viewModel.initCharactersList()
        recyclerView = binding.characterItemRecyclerView
        val adapter = CharacterAdapter()
        recyclerView.apply {
            recyclerView.adapter = adapter
            recyclerView.addOnScrollListener(this@CharacterFragment.scrollListener)
        }

        lifecycleScope.launch {
            viewModel.character.collect {
                adapter.charactersList = it
            }
        }

        binding.swiperefresh.setOnRefreshListener {
            viewModel.isAllowedPagination = true
            Log.d("REFRESH_DD", "is here")
            viewModel.isConnected = isNetworkConnected(requireContext())
            viewModel.initCharactersList()
            binding.swiperefresh.isRefreshing = false
        }
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false


    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!recyclerView.canScrollVertically(1) && dy > 0) {
                isNetworkConnected(requireContext())
                Log.d("Connecton_status", isNetworkConnected(requireContext()).toString())
                if (viewModel.isAllowedPagination) {
                    viewModel.isConnected = isNetworkConnected(requireContext())
                    viewModel.loadNextPage()
                }
                Log.d("PAGINATION", "WORK ")
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