
package com.tripmate.data
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Attraction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val category: String,
    val price: Int,
    val rating: Float,
    val description: String
)

//@Entity
//data class TripItem(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val attractionId: Int,
//    val day: Int,
//    val time: String
//)

@Entity
data class Post(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val location: String,
    val stars: Int,
    val imageUri: String? = null
)

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val bio: String = "Travel enthusiast"
)
