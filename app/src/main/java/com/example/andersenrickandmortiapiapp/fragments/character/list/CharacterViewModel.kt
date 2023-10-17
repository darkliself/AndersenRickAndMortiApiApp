package com.example.andersenrickandmortiapiapp.fragments.character.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andersenrickandmortiapiapp.data.mappers.toCharacterInfo
import com.example.andersenrickandmortiapiapp.repository.CharterSearchQuery
import com.example.andersenrickandmortiapiapp.repository.UseCase
import com.example.andersenrickandmortiapiapp.repository.RoomDbRepository
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.characters.character_list.CharacterInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.ceil


@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val roomDbRepository: RoomDbRepository,
    private val useCase: UseCase,
) : ViewModel() {

    private val _character = MutableStateFlow<List<CharacterInfo>>(emptyList())
    val character: MutableStateFlow<List<CharacterInfo>>
        get() = _character
    private val _error = MutableStateFlow(false)
    val error: MutableStateFlow<Boolean>
        get() = _error
    private var _isLoading = MutableStateFlow(false)
    val isLoading: MutableStateFlow<Boolean>
        get() = _isLoading

    var isAllowedPagination = true
    var isConnected = true
    private var pagesCount = 0
    private var pageIndex = 1
    private var elementsPerPage = 20

//    init {
//        viewModelScope.launch {
//            _isLoading.emit(true)
//            Log.d("PAGINATION", "INIT")
//            initEpisodesList()
//            _isLoading.emit(false)
//        }
//    }

    fun loadNextPage() {
        if (pageIndex >= pagesCount) {
            return
        }
        pageIndex++
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnected) {
                _character.emit(character.value.plus(useCase.getCharactersAndSaveToDb(pageIndex)))
            } else {
                roomDbRepository.db.characterDao.getAllCharacters().collect { list ->
                    if (list.isNotEmpty()) {
                        pagesCount = ceil(list.size / elementsPerPage.toFloat()).toInt()
                        _character.emit(list.map { it.toCharacterInfo() }
                            .take(elementsPerPage * pageIndex))
                    } else {
                        _error.emit(true)
                    }
                }
            }
        }
    }

    fun initCharactersList() {
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnected) {
                val queryResult = useCase.getCharactersAndSaveToDb(pageIndex)
                pagesCount = useCase.characterPagesCount
//            Log.d("PAGINATION_PROBLEM", pagesCount.toString())
                _character.emit(queryResult)
            } else {
                roomDbRepository.db.characterDao.getAllCharacters().collect { list ->
                    if (list.isNotEmpty()) {
                        pagesCount = ceil(list.size.toFloat() / elementsPerPage.toFloat()).toInt()
                        Log.d("PAGINATION", "page count<<< $pagesCount")
                        val result = list.map { it.toCharacterInfo() }.take(elementsPerPage)
                        _character.emit(result)
                    } else {
                        _error.emit(true)
                    }
                }
            }
        }
    }

    fun search(query: CharterSearchQuery) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("SEARCH_DATA", query.toString())
            var queryResult: List<CharacterInfo> = if (isConnected) {
                useCase.searchCharacters(query).listOfCharacters
            } else {
                roomDbRepository.db.characterDao.getFilteredCharacters(
                    name = query.name,
                    gender = query.gender,
                    species = query.species,
                    status = query.status,
                    type = query.type
                ).map { it.toCharacterInfo() }
            }
            if (queryResult.isNotEmpty()) {
                isAllowedPagination = false
                _character.emit(queryResult)
            }
        }
    }

}

