package com.tripmate.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao interface ItineraryDao {
  @Query("SELECT * FROM ItineraryItem ORDER BY day, startTime")
  fun items(): Flow<List<ItineraryItem>>
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun upsert(item: ItineraryItem)
  @Delete suspend fun delete(item: ItineraryItem)
}

@Dao interface PostDao {
  @Query("SELECT * FROM Post") fun posts(): Flow<List<Post>>
  @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun upsertAll(posts: List<Post>)
}

@Dao interface UserDao {
  @Query("SELECT * FROM User LIMIT 1") fun me(): Flow<User?>
  @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun upsert(user: User)
}

@Database(entities=[ItineraryItem::class, TripSummary::class, Post::class, User::class], version=1)
abstract class TripMateDb: RoomDatabase() {
  abstract fun itinerary(): ItineraryDao
  abstract fun posts(): PostDao
  abstract fun users(): UserDao
}
