package com.example.andersenrickandmortiapiapp.fragments.location.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andersenrickandmortiapiapp.data.mappers.toCharacterInfo
import com.example.andersenrickandmortiapiapp.data.mappers.toLocationInfo
import com.example.andersenrickandmortiapiapp.data.room.RoomDataBase
import com.example.andersenrickandmortiapiapp.repository.RetrofitRepository
import com.example.andersenrickandmortiapiapp.retrofit.models.characters.CharacterInfo
import com.example.andersenrickandmortiapiapp.retrofit.models.locatons.LocationInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LocationDetailsViewModel @Inject constructor(
    private val retrofitRepository: RetrofitRepository,
    db: RoomDataBase
) : ViewModel() {
    var isConnected = true
    private val locationDao = db.locationDao
    private val characterDao = db.characterDao
    private val _location = MutableStateFlow<LocationInfo?>(null)
    val location: MutableStateFlow<LocationInfo?>
        get() = _location
    private val _characters = MutableStateFlow<List<CharacterInfo>>(emptyList())
    val characters: MutableStateFlow<List<CharacterInfo>>
        get() = _characters

    fun getLocationDetails(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnected) {
                _location.emit(retrofitRepository.getLocationDetails(id))
            } else {

                val result = locationDao.getLocationById(id).toLocationInfo()
                val listOfCharacters = mutableListOf<String>()
                locationDao.getLocationWithCharacter(id).character_id.forEach {
                    listOfCharacters.add(it.characterId.toString())
                }
                result.residents = listOfCharacters
                _location.emit(result)
            }
            location.collect { data ->
                if (data != null) {
                    val arrayOfIds = mutableListOf<String>()
                    data.residents.forEach {
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
            _location.emit(null)
        }
    }
}