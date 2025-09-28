package com.tripmate.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TripItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val attractionId: Int,
    val day: Int,
    val time: String
)
