package com.example.andersenrickandmortiapiapp.data.room.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.andersenrickandmortiapiapp.data.room.model.entity.EpisodeEntity
import com.example.andersenrickandmortiapiapp.data.room.model.ralations.CharacterEpisodeCrossRef
import com.example.andersenrickandmortiapiapp.data.room.model.ralations.EpisodeWithCharacter
import kotlinx.coroutines.flow.Flow


@Dao
interface EpisodeDao {

    @Query("SELECT * FROM episode_table ORDER BY episode_id")
    fun getAllEpisodes(): Flow<List<EpisodeEntity>>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertEpisode(episode: EpisodeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfEpisode(episode: List<EpisodeEntity>)


    @Query("SELECT * FROM episode_table WHERE episode_id=:id")
    fun getEpisodeById(id:Int): EpisodeEntity

    @Query("SELECT * FROM episode_table WHERE episode_id IN(:id)")
    fun getListEpisodesById(id:List<Int>): Flow<List<EpisodeEntity>>

    @Query(
        "SELECT * FROM episode_table " +
                "WHERE (:name IS NULL OR name LIKE '%' || :name || '%') " +
                "AND (:episode IS NULL OR episode LIKE '%' || :episode || '%') "
    )
    fun getFilteredEpisodes(
        name: String?,
        episode: String?,
    ): List<EpisodeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacterEpisodeCrossRef(crossRef: CharacterEpisodeCrossRef)

    @Transaction
    @Query("SELECT * FROM episode_table WHERE episode_id = :id")
    fun getEpisodeWithCharacters(id: Int): EpisodeWithCharacter

}