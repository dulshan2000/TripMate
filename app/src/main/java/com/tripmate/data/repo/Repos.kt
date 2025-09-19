package com.tripmate.data.repo

import com.tripmate.data.local.*
import com.tripmate.domain.model.Attraction
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import android.content.Context

interface ItineraryRepo {
  fun items(): Flow<List<ItineraryItem>>
  suspend fun add(attractionId: String, day: Int, time: String, notes: String? = null)
  suspend fun remove(item: ItineraryItem)
}

class ItineraryRepoImpl(private val dao: ItineraryDao): ItineraryRepo {
  override fun items() = dao.items()
  override suspend fun add(attractionId: String, day: Int, time: String, notes: String?) {
    val id = "itm-" + System.currentTimeMillis()
    dao.upsert(ItineraryItem(id, day, time, attractionId, notes))
  }
  override suspend fun remove(item: ItineraryItem) = dao.delete(item)
}

interface PostsRepo { fun posts(): Flow<List<Post>> }

class PostsRepoImpl(private val dao: PostDao): PostsRepo {
  override fun posts() = dao.posts()
}

class SeedInitializer(private val context: Context, private val db: TripMateDb) {
  suspend fun runIfFirstLaunch() = withContext(Dispatchers.IO) {
    val prefs = context.getSharedPreferences("seed", Context.MODE_PRIVATE)
    if (prefs.getBoolean("done", false)) return@withContext
    val posts = loadJson<PostJson>("posts.json")
    db.posts().upsertAll(posts.map { Post(it.id, it.title, it.place, it.imageUrl, it.stars) })
    prefs.edit().putBoolean("done", true).apply()
  }

  private inline fun <reified T> loadJson(file: String): List<T> {
    val raw = context.assets.open("seed/$file").bufferedReader().readText()
    return Json { ignoreUnknownKeys = true }.decodeFromString(raw)
  }
}

@Serializable data class PostJson(val id:String, val title:String, val place:String, val imageUrl:String, val stars:Int)
