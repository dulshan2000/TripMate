package com.tripmate.ui.plan

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tripmate.data.AppDatabase
import com.tripmate.data.TripItem
import com.tripmate.databinding.ActivityTripFormBinding
import kotlinx.coroutines.launch

class TripFormActivity : AppCompatActivity() {

    private lateinit var b: ActivityTripFormBinding
    private val dao by lazy { AppDatabase.get(this).tripDao() }
    private var currentId: Int? = null
    private var currentTrip: TripItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityTripFormBinding.inflate(layoutInflater)
        setContentView(b.root)

        // Get ID from intent (if editing an existing TripItem)
        currentId = intent.getIntExtra("id", -1).let { if (it == -1) null else it }

        // Load data if editing
        lifecycleScope.launch {
            currentId?.let { id ->
                currentTrip = dao.getById(id)
                currentTrip?.let { t ->
                    b.etAttractionId.setText(t.attractionId.toString())
                    b.etDay.setText(t.day.toString())
                    b.etTime.setText(t.time)
                }
            }
        }

        // Save button (Insert or Update)
        b.btnSave.setOnClickListener {
            val aid = b.etAttractionId.text.toString().toIntOrNull()
            val day = b.etDay.text.toString().toIntOrNull()
            val time = b.etTime.text.toString()

            if (aid == null || day == null || time.isBlank()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                if (currentId == null) {
                    // Insert new
                    dao.insert(TripItem(attractionId = aid, day = day, time = time))
                } else {
                    // Update existing
                    currentTrip?.let {
                        val updated = it.copy(attractionId = aid, day = day, time = time)
                        Log.d("TripForm", "Updating: $updated")
                        dao.update(updated)
                    }
                }
                finish()
            }
        }

        // Delete button
        b.btnDelete.setOnClickListener {
            lifecycleScope.launch {
                currentTrip?.let {
                    Log.d("TripForm", "Deleting: $it")
                    dao.delete(it)
                }
                finish()
            }
        }
    }
}
