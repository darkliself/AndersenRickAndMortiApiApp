package com.example.andersenrickandmortiapiapp.fragments.location.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andersenrickandmortiapiapp.data.mappers.toCharacterInfo
import com.example.andersenrickandmortiapiapp.data.mappers.toLocationInfo
import com.example.andersenrickandmortiapiapp.data.room.ApplicationRoomDB
import com.example.andersenrickandmortiapiapp.repository.RickAndMortyRepository
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.characters.character_list.CharacterInfo
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.locatons.LocationInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LocationDetailsViewModel @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository,
    private val db: ApplicationRoomDB
) : ViewModel() {

    private val locationDao = db.locationDao
    private val characterDao = db.characterDao

    private val _location = MutableStateFlow<LocationInfo?>(null)
    val location: MutableStateFlow<LocationInfo?>
        get() = _location
    private val _characters = MutableStateFlow<List<CharacterInfo>>(emptyList())
    val characters: MutableStateFlow<List<CharacterInfo>>
        get() = _characters

    fun getLocationDetails(id: Int, isConnected: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnected) {
                _location.emit(rickAndMortyRepository.getLocationDetails(id))
            } else {
                _location.emit(locationDao.getLocationById(id).toLocationInfo())
            }
            location.collect { data ->
                if (data != null) {
                    val arrayOfIds = mutableListOf<String>()
                    data.residents.forEach {
                        arrayOfIds.add(it.split("/").last())
                    }
                    getCharacterListByID(arrayOfIds, isConnected)
                }
            }
        }
    }

    private suspend fun getCharacterListByID(idsList: List<String>, isConnected: Boolean) {
        if (isConnected) {
            val path = idsList.joinToString(",")
            _characters.emit(rickAndMortyRepository.getCharactersByIDs(path).filter { it.id > 0 })
        } else {
            characterDao.getCharacterListById(idsList).collect {
                if (it.isNotEmpty()) {
                    _characters.emit(it.map { it.toCharacterInfo() })
                }
            }
        }
    }
}