
package com.tripmate.vm
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import com.tripmate.data.Attraction
import com.tripmate.repo.Repos

class ExploreViewModel(app: Application): AndroidViewModel(app) {
    private val repo = Repos(app)
    private val all = repo.attractions.getAll()
    val filtered = MediatorLiveData<List<Attraction>>().apply { addSource(all) { value = it } }
    fun search(q: String) {
        val list = all.value ?: return
        filtered.value = list.filter { it.name.contains(q, true) || it.category.contains(q, true) }
    }
}
