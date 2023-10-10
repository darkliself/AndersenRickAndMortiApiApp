package com.example.andersenrickandmortiapiapp.fragments.character.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.andersenrickandmortiapiapp.databinding.FragmentEpisodesBinding
import com.example.andersenrickandmortiapiapp.databinding.FragmentSearchBinding
import com.example.andersenrickandmortiapiapp.fragments.character.CharacterViewModel


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharacterViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitButton.setOnClickListener {
            viewModel.search()
            val layoutParams = binding.searchLayout.layoutParams
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            binding.filtersList.visibility = View.GONE
            binding.searchLayout.layoutParams = layoutParams
        }

        binding.textInput2.setOnClickListener {
            Log.d("Fragment_click", "Clicked")
        }
        binding.textInput2.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                // Фокус получен, применить анимацию увеличения
//                val animation = AnimationUtils.loadAnimation(this, R.anim.resize_animation)
//                view.startAnimation(animation)
                Log.d("Fragment_click", "Yes")

                // Изменить размер (опционально)
                val layoutParams = binding.searchLayout.layoutParams
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT

                binding.filtersList.visibility = View.VISIBLE
                binding.searchLayout.layoutParams = layoutParams
            } else {
            }
        }
    }

}