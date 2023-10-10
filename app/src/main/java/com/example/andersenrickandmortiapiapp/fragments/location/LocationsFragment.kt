package com.example.andersenrickandmortiapiapp.fragments.location

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
import com.example.andersenrickandmortiapiapp.databinding.FragmentLocationsBinding
import kotlinx.coroutines.launch


class LocationsFragment : Fragment() {
    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LocationViewModel by activityViewModels()
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
        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.locationItemRecyclerView
//        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = LocationsAdapter()

        recyclerView.apply {
//            layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = adapter
            recyclerView.addOnScrollListener(this@LocationsFragment.scrollListener)
        }

        lifecycleScope.launch {
            viewModel.info.collect {
                adapter.locationsList = it
            }
        }


    }
    var isScrolling = false
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!recyclerView.canScrollVertically(1) && dy > 0)
            {
                viewModel.loadMoreLocation()
                Log.d("PAGINATION", "WORK ")
            }else if (!recyclerView.canScrollVertically(-1) && dy < 0)
            {

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