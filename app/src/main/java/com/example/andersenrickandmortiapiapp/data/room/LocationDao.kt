package com.example.andersenrickandmortiapiapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.andersenrickandmortiapiapp.data.room.model.EpisodeEntity
import com.example.andersenrickandmortiapiapp.data.room.model.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Query("SELECT * FROM location_table ORDER BY id")
    fun getAllLocations(): Flow<List<LocationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfLocations(episode: List<LocationEntity>)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertListOfLocations(episode: LocationEntity)

    @Query("SELECT * FROM location_table WHERE id=:id")
    fun getLocationById(id:Int): LocationEntity

    @Query("SELECT * FROM location_table WHERE id IN(:id)")
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
}
