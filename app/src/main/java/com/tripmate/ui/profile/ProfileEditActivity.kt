
package com.tripmate.ui.profile
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tripmate.data.AppDatabase
import com.tripmate.data.User
import com.tripmate.databinding.ActivityProfileEditBinding
import kotlinx.coroutines.launch
class ProfileEditActivity: AppCompatActivity(){
    private lateinit var b: ActivityProfileEditBinding
    private val dao by lazy { AppDatabase.get(this).userDao() }
    private var deleteRequested=false
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        b = ActivityProfileEditBinding.inflate(layoutInflater); setContentView(b.root)
        deleteRequested = intent.getBooleanExtra("delete", false)
        lifecycleScope.launch { dao.getUserNow()?.let{ u-> b.etName.setText(u.name); b.etEmail.setText(u.email); b.etBio.setText(u.bio) } }
        b.btnSave.setOnClickListener{
            val name=b.etName.text.toString(); val email=b.etEmail.text.toString(); val bio=b.etBio.text.toString()
            if(name.isBlank()||email.isBlank()){ Toast.makeText(this,"Name & Email required",Toast.LENGTH_SHORT).show(); return@setOnClickListener }
            lifecycleScope.launch {
                val u = dao.getUserNow()
                if(u==null) dao.insert(User(name=name,email=email,bio=bio))
                else dao.update(u.copy(name=name,email=email,bio=bio))
                finish()
            }
        }
        b.btnDelete.setOnClickListener{ lifecycleScope.launch{ dao.getUserNow()?.let{ dao.delete(it) }; finish() } }
        if(deleteRequested) b.btnDelete.performClick()
    }
}
