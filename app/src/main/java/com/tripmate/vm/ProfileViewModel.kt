
package com.tripmate.vm
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tripmate.data.User
import com.tripmate.repo.Repos
import kotlinx.coroutines.launch

class ProfileViewModel(app: Application): AndroidViewModel(app) {
    private val repo = Repos(app)
    val user = repo.users.getUser()
    fun ensure() = viewModelScope.launch {
        if (user.value == null) repo.users.insert(User(name="Dulshan Perera", email="dulshan@example.com", bio="Travel enthusiast"))
    }
}
