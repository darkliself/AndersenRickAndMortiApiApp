package com.example.andersenrickandmortiapiapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.andersenrickandmortiapiapp.data.room.model.CharacterEntity
import com.example.andersenrickandmortiapiapp.data.room.model.EpisodeEntity
import com.example.andersenrickandmortiapiapp.data.room.model.LocationEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface EpisodeDao {

    @Query("SELECT * FROM episode_table ORDER BY id")
    fun getAllEpisodes(): Flow<List<EpisodeEntity>>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertEpisode(episode: EpisodeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfEpisode(episode: List<EpisodeEntity>)


    @Query("SELECT * FROM episode_table WHERE id=:id")
    fun getEpisodeById(id:Int): EpisodeEntity

    @Query("SELECT * FROM episode_table WHERE id IN(:id)")
    fun getListEpisodesById(id:List<String>): Flow<List<EpisodeEntity>>

    @Query(
        "SELECT * FROM episode_table " +
                "WHERE (:name IS NULL OR name LIKE '%' || :name || '%') " +
                "AND (:episode IS NULL OR episode LIKE '%' || :episode || '%') "
    )
    fun getFilteredEpisodes(
        name: String?,
        episode: String?,
    ): List<EpisodeEntity>
}