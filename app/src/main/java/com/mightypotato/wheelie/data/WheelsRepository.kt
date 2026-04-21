package com.mightypotato.wheelie.data

import com.mightypotato.wheelie.data.local.Wheel
import com.mightypotato.wheelie.data.local.WheelDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository that provides access to [Wheel] data from the local database.
 *
 * This class abstracts the data source (Room) from the rest of the application
 * and provides domain-specific data mapping (e.g., extracting names from Wheel entities).
 */
class WheelsRepository(private val wheelDao: WheelDao) {

    /**
     * Returns a stream of wheel names from the database.
     *
     * @return A [Flow] emitting a list of names for all available wheels.
     */
    fun getWheels(): Flow<List<String>> {
        return wheelDao.getAllWheels().map { wheels ->
            wheels.map { it.name }
        }
    }

    /**
     * Inserts a wheel into the database.
     *
     * @param wheel The wheel entity to be persisted.
     * @return The row ID of the inserted item, or -1 if a conflict occurs.
     */
    suspend fun insertWheel(wheel: Wheel): Long {
        return wheelDao.insertWheel(wheel)
    }

    /**
     * Deletes all wheels matching the specified name.
     *
     * @param name The name of the wheel(s) to remove.
     */
    suspend fun deleteWheelByName(name: String) {
        wheelDao.deleteWheelByName(name)
    }
}
