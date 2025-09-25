
package com.tripmate.vm
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.tripmate.repo.Repos
class CommunityViewModel(app: Application): AndroidViewModel(app) {
    private val repo = Repos(app)
    val posts = repo.posts.getAll()
}
