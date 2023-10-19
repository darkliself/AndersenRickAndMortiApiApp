package com.example.andersenrickandmortiapiapp.fragments.location.list

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andersenrickandmortiapiapp.R
import com.example.andersenrickandmortiapiapp.data.mappers.toLocationInfo
import com.example.andersenrickandmortiapiapp.data.room.RoomDataBase
import com.example.andersenrickandmortiapiapp.repository.RetrofitRepository
import com.example.andersenrickandmortiapiapp.retrofit.models.characters.CharacterInfo
import com.example.andersenrickandmortiapiapp.retrofit.models.locatons.LocationInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class LocationViewModel @Inject constructor(
    private val roomDb: RoomDataBase,
    private val retrofitRepository: RetrofitRepository,
) : ViewModel() {
    var isConnected = true
    private var isAllowedPagination = true
    private val _location = MutableStateFlow<List<LocationInfo>>(emptyList())
    val location: MutableStateFlow<List<LocationInfo>>
        get() = _location
    private val _characters = MutableStateFlow<List<CharacterInfo>>(emptyList())
    val characters: MutableStateFlow<List<CharacterInfo>>
        get() = _characters
    private var _isLoading = MutableStateFlow(View.INVISIBLE)
    val isLoading: MutableStateFlow<Int>
        get() = _isLoading
    private var pagesCount = 0
    var pageIndex = 0
    private var elementsPerPage = 20

    fun getData() {
        if (pageIndex >= pagesCount && pagesCount != 0) {
            return
        }
        pageIndex++
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.emit(View.VISIBLE)
            if (isConnected) {
                val queryResult = retrofitRepository.getLocations(pageIndex)
                pagesCount = queryResult.info.pages
                _location.emit(location.value.plus(queryResult.locationsList))
            } else {
                roomDb.locationDao.getAllLocations().collect { list ->
                    if (list.isNotEmpty()) {
                        _location.emit(list.map { it.toLocationInfo() }
                            .take(elementsPerPage * pageIndex))
                    }
                    _isLoading.emit(View.INVISIBLE)
                }
            }
            _isLoading.emit(View.INVISIBLE)
        }
    }

    fun search(name: String, type: String, dimension: String, context: Context) {
        viewModelScope.launch {
            var queryResult = emptyList<LocationInfo>()
            if (isConnected) {
                withContext(Dispatchers.IO) {
                    retrofitRepository.searchLocations(name, type, dimension).let {
                        queryResult = it.locationsList
                    }
                }
            } else {
                withContext(Dispatchers.IO) {
                    queryResult = roomDb.locationDao.getFilteredLocations(
                        name = name,
                        type = type,
                        dimension = dimension
                    ).map { it.toLocationInfo() }
                }
            }
            if (queryResult.isNotEmpty()) {
                isAllowedPagination = false
                _location.emit(queryResult)
            } else {
                Toast.makeText(context, context.getString(R.string.no_results), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    fun refreshState() {
        viewModelScope.launch {
            _location.emit(emptyList())
        }
        pageIndex = 0
        isAllowedPagination = true
        getData()
    }
}