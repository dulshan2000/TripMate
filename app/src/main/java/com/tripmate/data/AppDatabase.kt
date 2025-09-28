
package com.tripmate.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Attraction::class, TripItem::class, Post::class, User::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun attractionDao(): AttractionDao
    abstract fun tripDao(): TripDao
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao
    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun get(ctx: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(ctx, AppDatabase::class.java, "tripmate.db")
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
    }
}
