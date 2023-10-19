package com.example.andersenrickandmortiapiapp.fragments.character.list

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andersenrickandmortiapiapp.R
import com.example.andersenrickandmortiapiapp.data.mappers.toCharacterInfo
import com.example.andersenrickandmortiapiapp.data.room.RoomDataBase
import com.example.andersenrickandmortiapiapp.repository.CharterSearchQuery
import com.example.andersenrickandmortiapiapp.repository.RetrofitRepository
import com.example.andersenrickandmortiapiapp.retrofit.models.characters.CharacterInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.ceil


@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val roomDb: RoomDataBase,
    private val retrofitRepository: RetrofitRepository,
) : ViewModel() {

    private val _character = MutableStateFlow<List<CharacterInfo>>(emptyList())
    val character: MutableStateFlow<List<CharacterInfo>>
        get() = _character
    private var _isLoading = MutableStateFlow(View.INVISIBLE)
    val isLoading: MutableStateFlow<Int>
        get() = _isLoading
    var isAllowedPagination = true
    var isConnected = true
    private var pagesCount = 0
    private var pageIndex = 0
    private var elementsPerPage = 20

    fun getData() {
        if (pageIndex >= pagesCount && pagesCount != 0) {
            return
        }
        pageIndex++
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.emit(View.VISIBLE)
            if (isConnected) {
                val queryResult = retrofitRepository.getCharacters(pageIndex)
                pagesCount = queryResult.info.pages
                _character.emit(character.value.plus(queryResult.listOfCharacters))
            } else {
                roomDb.characterDao.getAllCharacters().collect { list ->
                    if (list.isNotEmpty()) {
                        pagesCount = ceil(list.size / elementsPerPage.toFloat()).toInt()
                        _character.emit(list.map { it.toCharacterInfo() }
                            .take(elementsPerPage * pageIndex))
                    }
                    _isLoading.emit(View.INVISIBLE)
                }
            }
            _isLoading.emit(View.INVISIBLE)
        }
    }

    fun search(query: CharterSearchQuery, context: Context) {
        viewModelScope.launch {
            var queryResult: List<CharacterInfo> = emptyList()
            if (isConnected) {
                withContext(Dispatchers.IO) {
                    queryResult = retrofitRepository.searchCharacters(query).listOfCharacters
                }
            } else {
                withContext(Dispatchers.IO) {
                    roomDb.characterDao.getFilteredCharacters(
                        name = query.name,
                        gender = query.gender,
                        species = query.species,
                        status = query.status,
                        type = query.type
                    ).map { it.toCharacterInfo() }
                }
            }
            if (queryResult.isNotEmpty()) {
                isAllowedPagination = false
                _character.emit(queryResult)
            } else {
                Toast.makeText(context, context.getString(R.string.no_results), Toast.LENGTH_LONG).show()
            }
        }
    }

    fun refreshState() {
        viewModelScope.launch {
            _character.emit(emptyList())
        }
        pageIndex = 0
        isAllowedPagination = true
        getData()
    }
}

