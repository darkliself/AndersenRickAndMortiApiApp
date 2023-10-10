package com.example.andersenrickandmortiapiapp.fragments.character

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andersenrickandmortiapiapp.repository.RickAndMortyRepository
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.character_data.CharacterData
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.characters_list.CharacterInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository,
) : ViewModel() {

    private val _info = MutableStateFlow<List<CharacterInfo>>(emptyList())
    val info: MutableStateFlow<List<CharacterInfo>>
        get() = _info
    private var _isLoading = MutableStateFlow(false)
    val isLoading: MutableStateFlow<Boolean>
        get() = _isLoading
    private val _characterData = MutableStateFlow<CharacterData?>(null)
    val characterData: MutableStateFlow<CharacterData?>
        get() = _characterData
    private var count = 0
    private var pageIndex = 1

    init {
        viewModelScope.launch {
            _isLoading.emit(true)
            val queryResult = rickAndMortyRepository.getCharacters(pageIndex)
            _info.emit(queryResult.listOfCharacters)
            count = queryResult.info.pages
            _isLoading.emit(false)
        }
    }

    fun loadMoreCharacters() {
        if (pageIndex <= count) {
            viewModelScope.launch() {
                _isLoading.emit(true)
                _info.emit(info.value.plus(rickAndMortyRepository.getCharacters(pageIndex).listOfCharacters))
                _isLoading.emit(false)
                pageIndex++
            }
        }
    }

    fun getSingleCharacter(id: Int) {
        viewModelScope.launch() {
            _characterData.emit(rickAndMortyRepository.getSingleCharacter(id))
        }
    }

    fun search() {
        viewModelScope.launch {
           val z = rickAndMortyRepository.searchCharacters().listOfCharacters
            _info.emit(z)
            Log.d("SEARCH_DATA", z.toString())
        }
    }
}