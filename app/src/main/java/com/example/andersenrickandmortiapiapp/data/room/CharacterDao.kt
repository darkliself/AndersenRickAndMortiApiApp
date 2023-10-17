package com.example.andersenrickandmortiapiapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.andersenrickandmortiapiapp.data.room.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character_table ORDER BY id")
    fun getAllCharacters(): Flow<List<CharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(episode: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfCharacters(episode: List<CharacterEntity>)

    @Query("SELECT * FROM character_table WHERE id=:id")
    fun getCharacterById(id: Int): CharacterEntity

    @Query("SELECT * FROM character_table WHERE id IN(:id)")
    fun getCharacterListById(id: List<String>): Flow<List<CharacterEntity>>

    @Query(
        "SELECT * FROM character_table " +
                "WHERE (:name IS NULL OR name LIKE '%' || :name || '%') " +
                "AND (:gender IS NULL OR gender LIKE '%' || :gender || '%') " +
                "AND (:species IS NULL OR species LIKE '%' || :species || '%') " +
                "AND (:status IS NULL OR status LIKE '%' || :status || '%') " +
                "AND (:type IS NULL OR type LIKE '%' || :type || '%')"
    )
    fun getFilteredCharacters(
        gender: String?,
        name: String?,
        species: String?,
        status: String?,
        type: String?
    ): List<CharacterEntity>

}