package com.example.andersenrickandmortiapiapp.fragments.character.list.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.activityViewModels
import com.example.andersenrickandmortiapiapp.databinding.FragmentCharactersSearchBinding
import com.example.andersenrickandmortiapiapp.fragments.BaseFragment
import com.example.andersenrickandmortiapiapp.fragments.character.list.CharacterViewModel
import com.example.andersenrickandmortiapiapp.repository.CharterSearchQuery
import com.example.andersenrickandmortiapiapp.repository.noneToNull


class CharactersSearchFragment : BaseFragment() {
    private var _binding: FragmentCharactersSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharacterViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersSearchBinding.inflate(inflater, container, false)
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


    fun resetFilters() {
        binding.nameSearch.setText("")
        binding.speciesSearch.setText("")
        binding.genderGroup.check(binding.genderNone.id)
        binding.statusGroup.check(binding.statusNone.id)
        binding.filtersList.visibility = View.GONE
        binding.nameSearch.clearFocus()
        closeKeyboard()
    }

    private fun sendSearchRequest() {
        val selectedGenderID = binding.genderGroup.checkedRadioButtonId
        val selectedGenderOption = binding.root.findViewById<RadioButton>(selectedGenderID).text
        val selectedStatusID = binding.statusGroup.checkedRadioButtonId
        val selectedStatusOption = binding.root.findViewById<RadioButton>(selectedStatusID).text
        val nameQuery = binding.nameSearch.text
        val speciesQuery = binding.speciesSearch.text
        viewModel.isConnected = isNetworkConnected(requireContext())
        viewModel.search(
            query = CharterSearchQuery(
                name = nameQuery.toString(),
                status = selectedStatusOption.toString(),
                species = speciesQuery.toString(),
                gender = selectedGenderOption.toString()
            ).noneToNull(),
            context = requireContext()
        )
        binding.filtersList.visibility = View.GONE
        binding.nameSearch.clearFocus()
        closeKeyboard()
    }

}