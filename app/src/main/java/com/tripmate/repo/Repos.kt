
package com.tripmate.repo
import android.content.Context
import com.tripmate.data.AppDatabase

class Repos(ctx: Context) {
    private val db = AppDatabase.get(ctx)
    val attractions = db.attractionDao()
    val trips = db.tripDao()
    val posts = db.postDao()
    val users = db.userDao()

    fun getTopCommunityPosts() = posts.getTop5ByStars()
}
