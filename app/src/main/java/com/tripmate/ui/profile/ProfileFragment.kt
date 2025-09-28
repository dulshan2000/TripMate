
package com.tripmate.ui.profile
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tripmate.databinding.FragmentProfileBinding
import com.tripmate.vm.ProfileViewModel
import com.tripmate.ui.community.PostAdapter
import com.tripmate.repo.Repos

class ProfileFragment: Fragment(){
    private var _b: FragmentProfileBinding?=null; private val b get()=_b!!
    private val vm: ProfileViewModel by viewModels()
    private lateinit var adapter: PostAdapter
    override fun onCreateView(i: LayoutInflater,c: ViewGroup?,s: Bundle?)=FragmentProfileBinding.inflate(i,c,false).also{_b=it}.root
    override fun onViewCreated(v: View, s: Bundle?){
        vm.user.observe(viewLifecycleOwner){ u-> b.tvName.text=u?.name?:"No profile"; b.tvEmail.text=u?.email?:"-"; b.tvBio.text=u?.bio?:"-" }
        vm.ensure()
        adapter = PostAdapter(
            onEdit = {},
            onDelete = {}
        )
        b.rvMyPosts.layoutManager = LinearLayoutManager(requireContext()); b.rvMyPosts.adapter = adapter
        Repos(requireContext()).posts.getAll().observe(viewLifecycleOwner){ adapter.submit(it) }
        b.btnEdit.setOnClickListener { startActivity(Intent(requireContext(), ProfileEditActivity::class.java)) }
        b.btnDelete.setOnClickListener { startActivity(Intent(requireContext(), ProfileEditActivity::class.java).putExtra("delete", true)) }

        // Theme selector button
        b.btnTheme.setOnClickListener {
            val modes = arrayOf("Light", "Dark", "System Default")
            val current = when (androidx.appcompat.app.AppCompatDelegate.getDefaultNightMode()) {
                androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO -> 0
                androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES -> 1
                else -> 2
            }
            android.app.AlertDialog.Builder(requireContext())
                .setTitle("Choose Theme")
                .setSingleChoiceItems(modes, current) { dialog, which ->
                    val mode = when (which) {
                        0 -> androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
                        1 -> androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
                        else -> androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    }
                    androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(mode)
                    requireContext().getSharedPreferences("settings", android.content.Context.MODE_PRIVATE).edit().putInt("theme_mode", mode).apply()
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
    override fun onDestroyView(){_b=null;super.onDestroyView()}
}
