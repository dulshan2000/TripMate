package com.tripmate.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tripmate.R
import com.tripmate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        if (item.itemId == R.id.nav_theme) {
            val modes = arrayOf("Light", "Dark", "System Default")
            val current = when (androidx.appcompat.app.AppCompatDelegate.getDefaultNightMode()) {
                androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO -> 0
                androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES -> 1
                else -> 2
            }
            android.app.AlertDialog.Builder(this)
                .setTitle("Choose Theme")
                .setSingleChoiceItems(modes, current) { dialog, which ->
                    val mode = when (which) {
                        0 -> androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
                        1 -> androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
                        else -> androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    }
                    androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(mode)
                    getSharedPreferences("settings", MODE_PRIVATE).edit().putInt("theme_mode", mode).apply()
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel", null)
                .show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
    // Apply saved theme mode
    val mode = getSharedPreferences("settings", MODE_PRIVATE).getInt("theme_mode", -1)
    if (mode != -1) androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(mode)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> swap(HomeFragment())
                R.id.nav_explore -> swap(ExploreFragment())

                // Use fully-qualified names so this compiles regardless of imports:
                R.id.nav_plan -> swap(com.tripmate.ui.plan.PlanFragment())
                R.id.nav_community -> swap(com.tripmate.ui.community.CommunityFragment())
                R.id.nav_profile -> swap(com.tripmate.ui.profile.ProfileFragment())
            }
            true
        }

        binding.bottomNav.selectedItemId = R.id.nav_home
    }

    private fun swap(f: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, f).commit()
    }
}
