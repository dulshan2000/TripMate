package com.tripmate.ui.community

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Build
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tripmate.data.AppDatabase
import com.tripmate.data.Post
import com.tripmate.databinding.ActivityPostFormBinding
import kotlinx.coroutines.launch
import android.provider.MediaStore
import java.io.File

class PostFormActivity : AppCompatActivity() {
    private val REQUEST_PERMISSION = 102
    private lateinit var b: ActivityPostFormBinding
    private val dao by lazy { AppDatabase.get(this).postDao() }
    private var currentId: Int? = null
    private var imageUri: String? = null
    private val PICK_IMAGE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityPostFormBinding.inflate(layoutInflater); setContentView(b.root)
        currentId = intent.getIntExtra("id", -1).let { if (it == -1) null else it }
        imageUri = savedInstanceState?.getString("imageUri") ?: imageUri

        // Restore image if available
        if (!imageUri.isNullOrBlank()) {
            try {
                b.ivImage.setImageURI(Uri.parse(imageUri))
            } catch (e: Exception) {
                b.ivImage.setImageDrawable(null)
                Toast.makeText(this, "Image error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }

        // Load post for edit
        lifecycleScope.launch {
            try {
                currentId?.let { id ->
                    dao.getById(id)?.let { p ->
                        b.etTitle.setText(p.title)
                        b.etLocation.setText(p.location)
                        b.etStars.setText(p.stars.toString())
                        if (!p.imageUri.isNullOrBlank()) {
                            imageUri = p.imageUri
                            try {
                                b.ivImage.setImageURI(Uri.parse(imageUri))
                            } catch (e: Exception) {
                                b.ivImage.setImageDrawable(null)
                                Toast.makeText(this@PostFormActivity, "Image error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@PostFormActivity, "Load error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }

        b.btnSelectImage.setOnClickListener {
            val permission = if (Build.VERSION.SDK_INT >= 33) {
                android.Manifest.permission.READ_MEDIA_IMAGES
            } else {
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            }
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(permission), REQUEST_PERMISSION)
            } else {
                openImagePicker()
            }
            // Animate button for Material effect
            b.btnSelectImage.isPressed = true
            b.btnSelectImage.postDelayed({ b.btnSelectImage.isPressed = false }, 150)
        }

        b.btnSave.setOnClickListener {
            val title = b.etTitle.text.toString()
            val location = b.etLocation.text.toString()
            val starsStr = b.etStars.text.toString()
            val stars = starsStr.toIntOrNull() ?: -1
            var valid = true
            if (title.isBlank()) {
                b.etTitle.error = "Title required"
                valid = false
            } else {
                b.etTitle.error = null
            }
            if (location.isBlank()) {
                b.etLocation.error = "Location required"
                valid = false
            } else {
                b.etLocation.error = null
            }
            if (stars < 1 || stars > 5) {
                b.etStars.error = "Stars 1-5 required"
                valid = false
            } else {
                b.etStars.error = null
            }
            if (!valid) return@setOnClickListener
            lifecycleScope.launch {
                try {
                    if (currentId == null) {
                        dao.insert(Post(title = title, location = location, stars = stars, imageUri = imageUri ?: ""))
                    } else {
                        dao.update(Post(id = currentId!!, title = title, location = location, stars = stars, imageUri = imageUri ?: ""))
                    }
                    Toast.makeText(this@PostFormActivity, "Saved!", Toast.LENGTH_SHORT).show()
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@PostFormActivity, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }

        b.btnDelete.setOnClickListener {
            lifecycleScope.launch {
                try {
                    currentId?.let { id -> dao.getById(id)?.let { dao.delete(it) } }
                    Toast.makeText(this@PostFormActivity, "Deleted!", Toast.LENGTH_SHORT).show()
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@PostFormActivity, "Delete error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data
            if (uri != null) {
                try {
                    // Copy the image to app's private storage
                    val inputStream = contentResolver.openInputStream(uri)
                    val fileName = "post_image_${System.currentTimeMillis()}.jpg"
                    val file = File(filesDir, fileName)
                    inputStream?.use { input ->
                        file.outputStream().use { output: java.io.OutputStream ->
                            input.copyTo(output)
                        }
                    }
                    val newUri = Uri.fromFile(file)
                    imageUri = newUri.toString()
                    b.ivImage.setImageURI(newUri)
                    // Subtle fade-in animation for image preview
                    b.ivImage.alpha = 0f
                    b.ivImage.animate().alpha(1f).setDuration(250).start()
                } catch (e: SecurityException) {
                    b.ivImage.setImageDrawable(null)
                    Toast.makeText(this, "Permission denied for image. Please re-select.", Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    b.ivImage.setImageDrawable(null)
                    Toast.makeText(this, "Image error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (imageUri != null) {
            outState.putString("imageUri", imageUri)
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION) {
            val permission = if (Build.VERSION.SDK_INT >= 33) {
                android.Manifest.permission.READ_MEDIA_IMAGES
            } else {
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            }
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker()
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    Toast.makeText(this, "Permission denied permanently. Enable in app settings.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Permission denied. Cannot select image.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}