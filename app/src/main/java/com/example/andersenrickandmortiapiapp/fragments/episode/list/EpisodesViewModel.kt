package com.example.andersenrickandmortiapiapp.fragments.episode.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andersenrickandmortiapiapp.data.mappers.toEpisodesInfo
import com.example.andersenrickandmortiapiapp.repository.RoomDbRepository
import com.example.andersenrickandmortiapiapp.repository.UseCase
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.episodes.episodes_list.EpisodesInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.ceil


@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val roomDbRepository: RoomDbRepository,
    private val useCase: UseCase,
) : ViewModel() {
    var isAllowedPagination = true
    var isConnected = true
    private val _episodes = MutableStateFlow<List<EpisodesInfo>>(emptyList())
    val episodes: MutableStateFlow<List<EpisodesInfo>>
        get() = _episodes
    private val _error = MutableStateFlow(false)
    val error: MutableStateFlow<Boolean>
        get() = _error
    private var _isLoading = MutableStateFlow(false)
    val isLoading: MutableStateFlow<Boolean>
        get() = _isLoading
    private var pagesCount = 0
    private var pageIndex = 1
    private var elementsPerPage = 20

    fun loadNextPage() {
        if (pageIndex >= pagesCount) {
            return
        }
        pageIndex++
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnected) {
                _episodes.emit(episodes.value.plus(useCase.getEpisodesAndSaveToDb(pageIndex)))
            } else {
                roomDbRepository.db.episodeDao.getAllEpisodes().collect { list ->
                    if (list.isNotEmpty()) {
                        _episodes.emit(list.map { it.toEpisodesInfo() }
                            .take(elementsPerPage * pageIndex))
                    } else {
                        _error.emit(true)
                    }
                }
            }
        }
    }

    fun initEpisodesList() {
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnected) {
                val queryResult = useCase.getEpisodesAndSaveToDb(pageIndex)
                pagesCount = useCase.episodePagesCount
                _episodes.emit(queryResult)
            } else {
                roomDbRepository.db.episodeDao.getAllEpisodes().collect { list ->
                    if (list.isNotEmpty() && pagesCount == 0) {
                        pagesCount = ceil(list.size.toFloat() / elementsPerPage.toFloat()).toInt()
                        val result = list.map { it.toEpisodesInfo() }.take(elementsPerPage)
                        _episodes.emit(result)
                    } else {
                        _error.emit(true)
                    }
                }
            }
        }
    }

    fun search(name: String, episode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var queryResult = emptyList<EpisodesInfo>()
             if (isConnected) {
                 queryResult = useCase.searchEpisodes(name, episode).listOfEpisodes
            } else {
                 queryResult =  roomDbRepository.db.episodeDao.getFilteredEpisodes(
                    name = name,
                    episode = episode,

                    ).map { it.toEpisodesInfo() }
            }
            if (queryResult.isNotEmpty()) {
                isAllowedPagination = false
                _episodes.emit(queryResult)
            }
        }
    }
}