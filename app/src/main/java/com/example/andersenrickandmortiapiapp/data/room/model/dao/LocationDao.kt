package com.example.andersenrickandmortiapiapp.data.room.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.andersenrickandmortiapiapp.data.room.model.entity.LocationEntity
import com.example.andersenrickandmortiapiapp.data.room.model.ralations.LocationCharacterCrossRef
import com.example.andersenrickandmortiapiapp.data.room.model.ralations.LocationWithCharacters
import kotlinx.coroutines.flow.Flow


@Dao
interface LocationDao {

    @Query("SELECT * FROM location_table ORDER BY location_id")
    fun getAllLocations(): Flow<List<LocationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfLocations(episode: List<LocationEntity>)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertListOfLocations(episode: LocationEntity)

    @Query("SELECT * FROM location_table WHERE location_id=:id")
    fun getLocationById(id:Int): LocationEntity

    @Query("SELECT * FROM location_table WHERE location_id IN(:id)")
    fun getLocationListById(id:List<String>): Flow<List<LocationEntity>>

    @Query(
        "SELECT * FROM location_table " +
                "WHERE (:name IS NULL OR name LIKE '%' || :name || '%') " +
                "AND (:type IS NULL OR type LIKE '%' || :type || '%') " +
                "AND (:dimension IS NULL OR dimension LIKE '%' || :dimension || '%') "
    )
    fun getFilteredLocations(
        name: String?,
        type: String?,
        dimension: String?,
    ): List<LocationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocationCharacterCrossRef(crossRef: LocationCharacterCrossRef)

//    @Transaction
//    @Query("SELECT * FROM location_table2 WHERE location_id = :id")
//    fun getCharacterWithEpisodes(id: Int): CharacterWithEpisodes

    @Transaction
    @Query("SELECT * FROM location_table WHERE location_id = :id")
    fun getLocationWithCharacter(id: Int): LocationWithCharacters
}
