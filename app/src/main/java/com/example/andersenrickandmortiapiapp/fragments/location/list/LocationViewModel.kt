package com.example.andersenrickandmortiapiapp.fragments.location.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andersenrickandmortiapiapp.data.mappers.toEpisodesInfo
import com.example.andersenrickandmortiapiapp.data.mappers.toLocationInfo
import com.example.andersenrickandmortiapiapp.repository.UseCase
import com.example.andersenrickandmortiapiapp.repository.RoomDbRepository
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.characters.character_list.CharacterInfo
import com.example.andersenrickandmortiapiapp.retrofit.rick_and_morty.models.locatons.LocationInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.ceil


@HiltViewModel
class LocationViewModel @Inject constructor(
    private val roomDbRepository: RoomDbRepository,
    private val useCase: UseCase,
) : ViewModel() {

    var isConnected = true
    var isAllowedPagination = true

    private val _location = MutableStateFlow<List<LocationInfo>>(emptyList())
    val location: MutableStateFlow<List<LocationInfo>>
        get() = _location

    //    private val _locationInfo = MutableStateFlow<LocationInfo?>(null)
//    val locationInfo: MutableStateFlow<LocationInfo?>
//        get() = _locationInfo
    private val _characters = MutableStateFlow<List<CharacterInfo>>(emptyList())
    val characters: MutableStateFlow<List<CharacterInfo>>
        get() = _characters
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
                _location.emit(location.value.plus(useCase.getLocationsAndSaveToDb(pageIndex)))
            } else {
                roomDbRepository.db.locationDao.getAllLocations().collect { list ->
                    if (list.isNotEmpty()) {
                        Log.d("PAGINATION_LOCATION", list.size.toString())
                        _location.emit(list.map { it.toLocationInfo() }
                            .take(elementsPerPage * pageIndex))
                    } else {
                        _error.emit(true)
                    }
                }
            }
        }
    }

    fun initLocationList() {
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnected) {
                val queryResult = useCase.getLocationsAndSaveToDb(pageIndex)
                pagesCount = useCase.locationPagesCount
                Log.d("PAGINATION_PROBLEM", pagesCount.toString())
                _location.emit(queryResult)
            } else {
                roomDbRepository.db.locationDao.getAllLocations().collect { list ->
                    if (list.isNotEmpty() && pagesCount == 0) {
                        Log.d("PAGINATION", "page count<<< $pagesCount")
                        pagesCount = ceil(list.size.toFloat() / elementsPerPage.toFloat()).toInt()
                        val result = list.map { it.toLocationInfo() }.take(elementsPerPage)
                        _location.emit(result)
                    } else {
                        _error.emit(true)
                    }
                }
            }
        }
    }

    fun search(name: String, type: String, dimension: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var queryResult = emptyList<LocationInfo>()
            if (isConnected) {
                queryResult = useCase.searchLocation(name, type, dimension).locationsList
            } else {
                queryResult = roomDbRepository.db.locationDao.getFilteredLocations(
                    name = name,
                    type = type,
                    dimension = dimension
                ).map { it.toLocationInfo() }
            }
            if (queryResult.isNotEmpty()) {
                isAllowedPagination = false
                _location.emit(queryResult)
            }
        }
    }

    //    fun getLocationDetails(isConnected: Boolean, id: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            if (isConnected) {
//                _locationDetails.emit(useCase.getLocationDetails(id))
//            } else {
//                _locationDetails.emit(roomDbRepository.db.locationDao.getLocationById(id).toLocationInfo())
//            }
//            locationDetails.collect { data ->
//                if (data != null) {
//                    val arrayOfIds = mutableListOf<String>()
//                    data.residents.forEach {
//                        arrayOfIds.add(it.split("/").last())
//                    }
//                    getCharacters(isConnected, arrayOfIds)
//                }
//            }
//        }
//    }
}