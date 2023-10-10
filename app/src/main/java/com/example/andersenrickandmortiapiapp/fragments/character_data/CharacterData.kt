package com.example.andersenrickandmortiapiapp.fragments.character_data

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.andersenrickandmortiapiapp.databinding.FragmentCharacterDataBinding
import kotlinx.coroutines.launch

private const val ARG_CHARACTER_ID = "id"

class CharacterData : Fragment() {

    private var value: Int? = null
    private lateinit var recyclerView: RecyclerView
    private val viewModel: CharacterDataViewModel by activityViewModels()
    private var _binding: FragmentCharacterDataBinding? = null
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
        _binding = FragmentCharacterDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.locationsRecyclerView
        val adapter = CharacterDataAdapter()
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
                    binding.origin.text = data.origin.name + data.origin.url
                    binding.location.text = data.location.name + data.location.url
                    binding.created.text = data.created
                    adapter.episodesList = data.episode
                }
            }
        }
    }
}
