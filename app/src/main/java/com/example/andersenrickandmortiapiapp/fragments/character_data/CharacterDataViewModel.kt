package com.example.andersenrickandmortiapiapp.fragments.character_data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andersenrickandmortiapiapp.repository.RickAndMortyRepository
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.character_data.CharacterData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharacterDataViewModel @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository,
) : ViewModel() {

    private val _character = MutableStateFlow<CharacterData?>(null)
    val character: MutableStateFlow<CharacterData?>
        get() = _character

    fun loadSingleCharacter(id: Int) {
        viewModelScope.launch {
            _character.emit(rickAndMortyRepository.getSingleCharacter(id))
        }
    }
}