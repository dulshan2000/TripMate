
package com.tripmate.ui.plan
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tripmate.data.AppDatabase
import com.tripmate.data.TripItem
import com.tripmate.databinding.ActivityTripFormBinding
import kotlinx.coroutines.launch
class TripFormActivity: AppCompatActivity(){
    private lateinit var b: ActivityTripFormBinding
    private val dao by lazy { AppDatabase.get(this).tripDao() }
    private var currentId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        b = ActivityTripFormBinding.inflate(layoutInflater); setContentView(b.root)
        currentId = intent.getIntExtra("id",-1).let{ if(it==-1) null else it }
        lifecycleScope.launch {
            currentId?.let { id -> dao.getById(id)?.let { t-> b.etAttractionId.setText(t.attractionId.toString()); b.etDay.setText(t.day.toString()); b.etTime.setText(t.time) } }
        }
        b.btnSave.setOnClickListener{
            val aid=b.etAttractionId.text.toString().toIntOrNull()
            val day=b.etDay.text.toString().toIntOrNull()
            val time=b.etTime.text.toString()
            if(aid==null||day==null||time.isBlank()){ Toast.makeText(this,"Fill all fields",Toast.LENGTH_SHORT).show(); return@setOnClickListener }
            lifecycleScope.launch {
                if(currentId==null) dao.insert(TripItem(attractionId=aid,day=day,time=time))
                else dao.update(TripItem(id=currentId!!,attractionId=aid,day=day,time=time))
                finish()
            }
        }
        b.btnDelete.setOnClickListener{
            lifecycleScope.launch { currentId?.let{ id-> dao.getById(id)?.let{ dao.delete(it) } }; finish() }
        }
    }
}
