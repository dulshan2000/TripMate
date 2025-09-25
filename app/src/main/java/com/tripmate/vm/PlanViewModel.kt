
package com.tripmate.vm
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.tripmate.repo.Repos
class PlanViewModel(app: Application): AndroidViewModel(app) {
    private val repo = Repos(app)
    val items = repo.trips.getAll()
}
