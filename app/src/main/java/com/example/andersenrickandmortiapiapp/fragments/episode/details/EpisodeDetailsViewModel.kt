package com.example.andersenrickandmortiapiapp.fragments.episode.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andersenrickandmortiapiapp.data.mappers.toCharacterInfo
import com.example.andersenrickandmortiapiapp.data.mappers.toEpisodesInfo
import com.example.andersenrickandmortiapiapp.data.room.RoomDataBase
import com.example.andersenrickandmortiapiapp.repository.RetrofitRepository
import com.example.andersenrickandmortiapiapp.retrofit.models.characters.CharacterInfo
import com.example.andersenrickandmortiapiapp.retrofit.models.episodes.EpisodesInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailsViewModel @Inject constructor(
    private val retrofitRepository: RetrofitRepository,
    db: RoomDataBase
) : ViewModel() {
    private val episodeDao = db.episodeDao
    private val characterDao = db.characterDao
    var isConnected = true

    private val _episode = MutableStateFlow<EpisodesInfo?>(null)
    val episode: MutableStateFlow<EpisodesInfo?>
        get() = _episode
    private val _characters = MutableStateFlow<List<CharacterInfo>>(emptyList())
    val characters: MutableStateFlow<List<CharacterInfo>>
        get() = _characters


    fun getEpisodeDetails(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnected) {
                _episode.emit(retrofitRepository.getEpisodeDetail(id))
            } else {
                val result = episodeDao.getEpisodeById(id).toEpisodesInfo()
                val listOfCharacters = mutableListOf<String>()
                episodeDao.getEpisodeWithCharacters(id).character_id.forEach {
                    listOfCharacters.add(it.characterId.toString())
                }
                result.characters = listOfCharacters
                _episode.emit(result)
            }
            episode.collect { data ->
                if (data != null) {
                    val arrayOfIds = mutableListOf<String>()
                    data.characters.forEach {
                        arrayOfIds.add(it.split("/").last())
                    }
                    getCharactersByID(arrayOfIds)
                }
            }
        }
    }

    private suspend fun getCharactersByID(idsList: List<String>) {
        if (isConnected) {
            val path = idsList.joinToString(",")
            _characters.emit(retrofitRepository.getCharactersByIDs(path))
        } else {
            characterDao.getCharacterListById(idsList).collect { list ->
                if (list.isNotEmpty()) {
                    _characters.emit(list.map { it.toCharacterInfo() })
                }
            }
        }
    }

    fun clearData() {
        viewModelScope.launch {
            _episode.emit(null)
        }
    }
}