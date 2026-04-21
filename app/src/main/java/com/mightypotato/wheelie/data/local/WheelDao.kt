package com.mightypotato.wheelie.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the wheels table.
 *
 * Provides methods for querying, inserting, and deleting [Wheel] entities.
 */
@Dao
interface WheelDao {
    /**
     * Observes all wheels in the database.
     *
     * @return A [Flow] emitting the list of all wheels ordered by creation date descending.
     */
    @Query("SELECT * FROM wheels ORDER BY creationDate DESC")
    fun getAllWheels(): Flow<List<Wheel>>

    /**
     * Inserts a new wheel into the database.
     *
     * If a wheel with the same unique keys (name and owner) already exists,
     * the operation is ignored.
     *
     * @param wheel The wheel to insert.
     * @return The row ID of the inserted item, or -1 if the insertion was ignored.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWheel(wheel: Wheel): Long

    /**
     * Deletes a specific wheel from the database.
     *
     * @param wheel The wheel to delete.
     */
    @Delete
    suspend fun deleteWheel(wheel: Wheel)

    /**
     * Deletes wheels with the specified name.
     *
     * @param name The name of the wheel(s) to delete.
     */
    @Query("DELETE FROM wheels WHERE name = :name")
    suspend fun deleteWheelByName(name: String)
}
