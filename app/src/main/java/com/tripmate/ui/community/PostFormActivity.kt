
package com.tripmate.ui.community
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tripmate.data.AppDatabase
import com.tripmate.data.Post
import com.tripmate.databinding.ActivityPostFormBinding
import kotlinx.coroutines.launch
class PostFormActivity: AppCompatActivity(){
    private lateinit var b: ActivityPostFormBinding
    private val dao by lazy { AppDatabase.get(this).postDao() }
    private var currentId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        b = ActivityPostFormBinding.inflate(layoutInflater); setContentView(b.root)
        currentId = intent.getIntExtra("id",-1).let{ if(it==-1) null else it }
        lifecycleScope.launch { currentId?.let{ id-> dao.getById(id)?.let{ p-> b.etTitle.setText(p.title); b.etLocation.setText(p.location); b.etStars.setText(p.stars.toString()) } } }
        b.btnSave.setOnClickListener{
            val title=b.etTitle.text.toString(); val location=b.etLocation.text.toString(); val stars=b.etStars.text.toString().toIntOrNull()
            if(title.isBlank()||location.isBlank()||stars==null){ Toast.makeText(this,"Fill all fields",Toast.LENGTH_SHORT).show(); return@setOnClickListener }
            lifecycleScope.launch{
                if(currentId==null) dao.insert(Post(title=title, location=location, stars=stars))
                else dao.update(Post(id=currentId!!, title=title, location=location, stars=stars))
                finish()
            }
        }
        b.btnDelete.setOnClickListener{ lifecycleScope.launch{ currentId?.let{ id-> dao.getById(id)?.let{ dao.delete(it) } }; finish() } }
    }
}
