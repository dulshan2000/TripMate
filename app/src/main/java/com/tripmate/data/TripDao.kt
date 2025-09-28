
package com.tripmate.data
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AttractionDao {
    @Query("SELECT * FROM Attraction ORDER BY rating DESC")
    fun getAll(): LiveData<List<Attraction>>
    @Insert suspend fun insert(a: Attraction): Long
    @Update suspend fun update(a: Attraction)
    @Delete suspend fun delete(a: Attraction)
}

@Dao
interface TripDao {
    @Query("SELECT * FROM TripItem ORDER BY day, time")
    fun getAll(): LiveData<List<TripItem>>
    @Query("SELECT * FROM TripItem WHERE id=:id") suspend fun getById(id: Int): TripItem?
    @Insert suspend fun insert(t: TripItem): Long
    @Update suspend fun update(t: TripItem)
    @Delete suspend fun delete(t: TripItem)
}

@Dao
interface PostDao {
    @Query("SELECT * FROM Post ORDER BY id DESC")
    fun getAll(): LiveData<List<Post>>

    @Query("SELECT * FROM Post ORDER BY stars DESC, id DESC LIMIT 5")
    fun getTop5ByStars(): LiveData<List<Post>>
    @Query("SELECT * FROM Post WHERE id=:id") suspend fun getById(id: Int): Post?
    @Insert suspend fun insert(p: Post): Long
    @Update suspend fun update(p: Post)
    @Delete suspend fun delete(p: Post)
}

@Dao
interface UserDao {
    @Query("SELECT * FROM User LIMIT 1")
    fun getUser(): LiveData<User?>
    @Query("SELECT * FROM User LIMIT 1")
    suspend fun getUserNow(): User?
    @Insert suspend fun insert(u: User): Long
    @Update suspend fun update(u: User)
    @Delete suspend fun delete(u: User)
}
