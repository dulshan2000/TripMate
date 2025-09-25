package com.tripmate.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tripmate.R
import com.tripmate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
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
