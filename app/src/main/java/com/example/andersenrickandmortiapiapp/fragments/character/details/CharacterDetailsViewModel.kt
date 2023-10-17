package com.example.andersenrickandmortiapiapp.fragments.character.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andersenrickandmortiapiapp.data.mappers.toCharacterInfo
import com.example.andersenrickandmortiapiapp.data.mappers.toEpisodesInfo
import com.example.andersenrickandmortiapiapp.data.room.ApplicationRoomDB
import com.example.andersenrickandmortiapiapp.repository.RickAndMortyRepository
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.characters.character_list.CharacterInfo
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.episodes.episodes_list.EpisodesInfo
import com.example.andersenrickandmortiapiapp.utils.UrlParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository,
    db: ApplicationRoomDB
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

    fun loadSingleCharacter(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnected) {
                _character.emit(rickAndMortyRepository.getCharacterDetails(id))
            } else {
                _character.emit(characterDao.getCharacterById(id).toCharacterInfo())
            }
            character.collect { data ->
                if (data != null) {
                    val arrayOfIds = mutableListOf<String>()
                    data.episode.forEach {
                        arrayOfIds.add(UrlParser.getIdFromUrl(it))
                    }
                    getEpisodesByID(arrayOfIds)
                }
            }
        }

    }

    private suspend fun getEpisodesByID(idsList: List<String>) {
        if (isConnected) {
            _episode.emit(rickAndMortyRepository.getEpisodesByID(idsList))
        } else {
            episodeDao.getListEpisodesById(idsList).collect { list ->
                if (list.isNotEmpty()) {
                    _episode.emit(list.map { it.toEpisodesInfo() })
                }
            }
        }
    }
}