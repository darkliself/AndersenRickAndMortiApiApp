package com.example.andersenrickandmortiapiapp.fragments.location

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andersenrickandmortiapiapp.repository.RickAndMortyRepository
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.locatons.LocationInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LocationViewModel @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository,
) : ViewModel() {

    private val _info = MutableStateFlow<List<LocationInfo>>(emptyList())
    val info: MutableStateFlow<List<LocationInfo>>
        get() = _info
    private var _isLoading = MutableStateFlow(false)
    val isLoading: MutableStateFlow<Boolean>
        get() = _isLoading
    private var count = 0
    private var pageIndex = 1

    init {
        viewModelScope.launch {
            _isLoading.emit(true)
            val queryResult = rickAndMortyRepository.getLocations(pageIndex)
            _info.emit(queryResult.locationsList)
            count = queryResult.info.pages
            _isLoading.emit(false)
        }
    }

    fun loadMoreLocation() {
        if (pageIndex <= count) {
            Log.d("PAGINATION", "current page $pageIndex << count = $count")
            viewModelScope.launch() {
                _isLoading.emit(true)
                _info.emit( info.value.plus(rickAndMortyRepository.getLocations(pageIndex).locationsList))
                pageIndex++
                _isLoading.emit(false)
            }
        }
    }
}