package com.example.andersenrickandmortiapiapp.fragments.episode

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andersenrickandmortiapiapp.repository.RickAndMortyRepository
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.episodes.EpisodesInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val repository: RickAndMortyRepository,
) : ViewModel() {

    private val _info = MutableStateFlow<List<EpisodesInfo>>(emptyList())
    val info: MutableStateFlow<List<EpisodesInfo>>
        get() = _info
    private var _isLoading = MutableStateFlow(false)
    val isLoading: MutableStateFlow<Boolean>
        get() = _isLoading
    private var count = 0
    private var pageIndex = 1

    init {
        viewModelScope.launch {
            _isLoading.emit(true)
            val queryResult = repository.getEpisodes(pageIndex)
            _info.emit(queryResult.listOfEpisodes)
            count = queryResult.info.pages
            _isLoading.emit(false)
        }
    }

    fun loadNextPage() {
        if (pageIndex <= count) {
            viewModelScope.launch() {
                Log.d("PAGINATION", "current page $pageIndex << count = $count")
                _isLoading.emit(true)
                _info.emit(info.value.plus(repository.getEpisodes(pageIndex).listOfEpisodes))
                _isLoading.emit(false)
                pageIndex++
            }
        }
    }
}