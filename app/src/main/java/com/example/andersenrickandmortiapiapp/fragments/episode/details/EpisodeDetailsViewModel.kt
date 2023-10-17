package com.example.andersenrickandmortiapiapp.fragments.episode.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andersenrickandmortiapiapp.data.mappers.toCharacterInfo
import com.example.andersenrickandmortiapiapp.data.mappers.toEpisodesInfo
import com.example.andersenrickandmortiapiapp.data.room.ApplicationRoomDB
import com.example.andersenrickandmortiapiapp.repository.RickAndMortyRepository
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.characters.character_list.CharacterInfo
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.episodes.episodes_list.EpisodesInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailsViewModel @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository,
    private val db: ApplicationRoomDB
) : ViewModel() {
    val episodeDao = db.episodeDao
    val characterDao = db.characterDao
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
                _episode.emit(rickAndMortyRepository.getEpisodeDetail(id))
            } else {
                _episode.emit(episodeDao.getEpisodeById(id).toEpisodesInfo())
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
            _characters.emit(rickAndMortyRepository.getCharactersByIDs(path))
        } else {
            characterDao.getCharacterListById(idsList).collect { list ->
                if (list.isNotEmpty()) {
                    _characters.emit(list.map { it.toCharacterInfo() })
                }
            }
        }
    }
}