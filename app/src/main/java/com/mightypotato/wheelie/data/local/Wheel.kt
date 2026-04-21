package com.mightypotato.wheelie.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "wheels",
    indices = [Index(value = ["name", "owner"], unique = true)]
)
data class Wheel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val owner: String,
    val creationDate: Long = System.currentTimeMillis()
)
