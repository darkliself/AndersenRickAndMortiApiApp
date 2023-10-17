package com.example.andersenrickandmortiapiapp.fragments.location.details

//@HiltViewModel
//class LocationDetailsViewModel @Inject constructor(
//    private val rickAndMortyRepository: RickAndMortyRepository,
//) : ViewModel() {
//
//    private val _location = MutableStateFlow<LocationDetails?>(null)
//    val location: MutableStateFlow<LocationDetails?>
//        get() = _location
//    private val _characters = MutableStateFlow<List<CharacterInfo>>(emptyList())
//    val characters: MutableStateFlow<List<CharacterInfo>>
//        get() = _characters
//
//    fun getLocationDetails(id: Int) {
//        viewModelScope.launch() {
//            try {
//                _location.emit(rickAndMortyRepository.getLocationDetails(id))
//            } catch (e: Exception) {
//                Log.d("PROGLEM", e.message.toString())
//            }
//
//            location.collect { data ->
//                if (data != null) {
//                    val arrayOfIds = mutableListOf<String>()
//                    data.residents.forEach {
//                        arrayOfIds.add(it.split("/").last())
//                    }
//                    getEpisodesByID(arrayOfIds)
//                }
//            }
//        }
//    }
//
//    private fun getEpisodesByID(idsList: List<String>) {
//        viewModelScope.launch {
//            val path = if (idsList.size == 1) {
//                idsList[0] + ","
//            } else {
//                idsList.joinToString(",")
//            }
//            _characters.emit(rickAndMortyRepository.getCharactersByIDs(path).filter { it.id > 0 })
//            Log.d("TEST_REQUEST", path.toString())
//        }
//    }
//}