
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
        adapter = PostAdapter { /* could open editor */ }
        b.rvMyPosts.layoutManager = LinearLayoutManager(requireContext()); b.rvMyPosts.adapter = adapter
        Repos(requireContext()).posts.getAll().observe(viewLifecycleOwner){ adapter.submit(it) }
        b.btnEdit.setOnClickListener { startActivity(Intent(requireContext(), ProfileEditActivity::class.java)) }
        b.btnDelete.setOnClickListener { startActivity(Intent(requireContext(), ProfileEditActivity::class.java).putExtra("delete", true)) }
    }
    override fun onDestroyView(){_b=null;super.onDestroyView()}
}
