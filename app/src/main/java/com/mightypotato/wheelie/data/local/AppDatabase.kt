package com.mightypotato.wheelie.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Main Room database class for the application.
 *
 * Defines [Wheel] as its primary entity and provides the main access point
 * to the persisted data via [WheelDao].
 */
@Database(entities = [Wheel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Accessor for the [WheelDao] to perform CRUD operations on the wheels table.
     */
    abstract fun wheelDao(): WheelDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        /**
         * Returns the singleton [AppDatabase] instance.
         *
         * Uses double-checked locking to ensure thread-safe initialization.
         *
         * @param context Application context used to build the Room database.
         * @return The single instance of [AppDatabase].
         */
        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "wheelie_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
