package com.tripmate.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity data class ItineraryItem(
  @PrimaryKey val id:String,
  val day:Int, val startTime:String, val attractionId:String, val notes:String? = null
)

@Entity data class TripSummary(
  @PrimaryKey val tripId:String, val days:Int, val places:Int, val estCost:Int, val bestSeason:String
)

@Entity data class Post(
  @PrimaryKey val id:String, val title:String, val place:String, val imageUrl:String, val stars:Int
)

@Entity data class User(
  @PrimaryKey val id:String, val name:String, val email:String, val avatarUrl:String?, val bio:String?
)
