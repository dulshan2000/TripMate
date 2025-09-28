
package com.tripmate.vm
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tripmate.data.Attraction
import com.tripmate.repo.Repos
import kotlinx.coroutines.launch

class HomeViewModel(app: Application): AndroidViewModel(app) {
    private val repo = Repos(app)
    val featured = repo.attractions.getAll()
    val topCommunityPosts = repo.getTopCommunityPosts()
    fun seed() = viewModelScope.launch {
        if (featured.value.isNullOrEmpty()) {
            repo.attractions.insert(Attraction(name="Sigiriya Rock", category="Historical", price=30, rating=4.8f, description="Ancient fortress"))
            repo.attractions.insert(Attraction(name="Yala Safari", category="Wildlife", price=50, rating=4.6f, description="Safari tour"))
        }
    }
}
