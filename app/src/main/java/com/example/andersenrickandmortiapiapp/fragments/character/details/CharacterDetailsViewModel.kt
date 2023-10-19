package com.example.andersenrickandmortiapiapp.fragments.character.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andersenrickandmortiapiapp.data.mappers.toCharacterInfo
import com.example.andersenrickandmortiapiapp.data.mappers.toEpisodesInfo
import com.example.andersenrickandmortiapiapp.data.room.RoomDataBase
import com.example.andersenrickandmortiapiapp.repository.RetrofitRepository
import com.example.andersenrickandmortiapiapp.retrofit.models.characters.CharacterInfo
import com.example.andersenrickandmortiapiapp.retrofit.models.episodes.EpisodesInfo
import com.example.andersenrickandmortiapiapp.utils.UrlParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val retrofitRepository: RetrofitRepository,
    db: RoomDataBase
) : ViewModel() {

    var isConnected = true
    private val episodeDao = db.episodeDao
    private val characterDao = db.characterDao

    private val _character = MutableStateFlow<CharacterInfo?>(null)
    val character: MutableStateFlow<CharacterInfo?>
        get() = _character

    private val _episode = MutableStateFlow<List<EpisodesInfo>>(emptyList())
    val episode: MutableStateFlow<List<EpisodesInfo>>
        get() = _episode

    fun getCharacterDetails(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnected) {
                _character.emit(retrofitRepository.getCharacterDetails(id))
            } else {
                val result = characterDao.getCharacterById(id).toCharacterInfo()
                val listOfEpisodesIds = mutableListOf<String>()
                characterDao.getCharacterWithEpisodes(id).episode_id.forEach {
                    listOfEpisodesIds.add(it.episodeId.toString())
                }
                result.episode = listOfEpisodesIds
                _character.emit(result)
            }
            character.collectLatest { data ->
                if (data != null) {
                    val arrayOfIds = mutableListOf<Int>()
                    data.episode.forEach {
                        arrayOfIds.add(UrlParser.getIdFromUrl(it))
                    }
                    getEpisodesByID(arrayOfIds)
                }
            }
        }

    }

    private suspend fun getEpisodesByID(idsList: List<Int>) {
        if (isConnected) {
            _episode.emit(retrofitRepository.getEpisodesByID(idsList))
        } else {
            episodeDao.getListEpisodesById(idsList).collect { list ->
                if (list.isNotEmpty()) {
                    _episode.emit(list.map { it.toEpisodesInfo() })
                }
            }
        }
    }

    fun clearData() {
        viewModelScope.launch {
            _character.emit(null)
        }
    }
}