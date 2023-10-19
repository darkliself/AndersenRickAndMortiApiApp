package com.example.andersenrickandmortiapiapp.fragments.episode.list

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andersenrickandmortiapiapp.R
import com.example.andersenrickandmortiapiapp.data.mappers.toEpisodesInfo
import com.example.andersenrickandmortiapiapp.data.room.RoomDataBase
import com.example.andersenrickandmortiapiapp.repository.RetrofitRepository
import com.example.andersenrickandmortiapiapp.retrofit.models.episodes.EpisodesInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val roomDb: RoomDataBase,
    private val retrofitRepository: RetrofitRepository
) : ViewModel() {
    private var isAllowedPagination = true
    var isConnected = true
    private val _episodes = MutableStateFlow<List<EpisodesInfo>>(emptyList())
    val episodes: MutableStateFlow<List<EpisodesInfo>>
        get() = _episodes
    private var _isLoading = MutableStateFlow(View.INVISIBLE)
    val isLoading: MutableStateFlow<Int>
        get() = _isLoading

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
                val queryResult = retrofitRepository.getEpisodes(pageIndex)
                pagesCount = queryResult.info.pages
                _episodes.emit(episodes.value.plus(queryResult.listOfEpisodes))
            } else {
                roomDb.episodeDao.getAllEpisodes().collect { list ->
                    if (list.isNotEmpty()) {
                        _episodes.emit(list.map { it.toEpisodesInfo() }
                            .take(elementsPerPage * pageIndex))
                    }
                    _isLoading.emit(View.INVISIBLE)
                }
            }
            _isLoading.emit(View.INVISIBLE)
        }
    }

    fun search(name: String, episode: String, context: Context) {
        viewModelScope.launch {
            var queryResult = emptyList<EpisodesInfo>()
            if (isConnected) {
                withContext(Dispatchers.IO) {
                    retrofitRepository.searchEpisodes(name, episode).let {
                        if (it != null) {
                            queryResult = it.listOfEpisodes
                        }
                    }
                }
            } else {
                withContext(Dispatchers.IO) {
                    queryResult = roomDb.episodeDao.getFilteredEpisodes(
                        name = name,
                        episode = episode,
                    ).map { it.toEpisodesInfo() }
                }
            }
            if (queryResult.isNotEmpty()) {
                isAllowedPagination = false
                _episodes.emit(queryResult)
            } else {
                Toast.makeText(context, context.getString(R.string.no_results), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    fun refreshState() {
        viewModelScope.launch {
            _episodes.emit(emptyList())
        }
        pageIndex = 0
        isAllowedPagination = true
        getData()
    }
}