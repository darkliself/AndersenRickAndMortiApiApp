package com.example.andersenrickandmortiapiapp.fragments.character_data

import com.example.andersenrickandmortiapiapp.repository.RickAndMortyRepository
import javax.inject.Inject

class CharacterDataUseCase @Inject constructor(val repository: RickAndMortyRepository) {
    suspend fun getCharacter(id: Int) {
        repository.getSingleCharacter(id)
    }
}