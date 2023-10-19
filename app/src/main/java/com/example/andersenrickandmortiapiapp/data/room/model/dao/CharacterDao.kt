package com.example.andersenrickandmortiapiapp.data.room.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.andersenrickandmortiapiapp.data.room.model.entity.CharacterEntity
import com.example.andersenrickandmortiapiapp.data.room.model.ralations.CharacterEpisodeCrossRef
import com.example.andersenrickandmortiapiapp.data.room.model.ralations.CharacterWithEpisodes
import kotlinx.coroutines.flow.Flow


@Dao
interface CharacterDao {

    @Query("SELECT * FROM character_table ORDER BY character_id")
    fun getAllCharacters(): Flow<List<CharacterEntity>>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertCharacter(character: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfCharacters(character: List<CharacterEntity>)

    @Query("SELECT * FROM character_table WHERE character_id=:id")
    fun getCharacterById(id: Int): CharacterEntity

    @Query("SELECT * FROM character_table WHERE character_id IN(:id)")
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacterEpisodeCrossRef(crossRef: CharacterEpisodeCrossRef)

    @Transaction
    @Query("SELECT * FROM character_table WHERE character_id = :id")
    fun getCharacterWithEpisodes(id: Int): CharacterWithEpisodes

//    @Transaction
//    @Query("SELECT * FROM episode_table WHERE episode_id = :id")
//    fun getEpisodeWithCharacter(id: Int): List<EpisodeWithCharacter>

//    @Dao
//    interface CharacterEpisodeDao {
//        @Query("SELECT characters.name AS characterName, episodes.name AS episodeName " +
//                "FROM character_table AS characters " +
//                "INNER JOIN episode_table AS episodes ON characters.id = episodes.character_id")
//        fun getCharacterEpisodes(): List<CharacterEpisodeResult>
//    }

}