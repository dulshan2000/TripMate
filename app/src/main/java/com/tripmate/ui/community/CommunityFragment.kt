
package com.tripmate.ui.community
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tripmate.databinding.FragmentCommunityBinding
import com.tripmate.vm.CommunityViewModel
class CommunityFragment: Fragment(){
    private var _b: FragmentCommunityBinding?=null; private val b get()=_b!!
    private val vm: CommunityViewModel by viewModels()
    private lateinit var adapter: PostAdapter
    override fun onCreateView(i: LayoutInflater,c: ViewGroup?,s: Bundle?)=FragmentCommunityBinding.inflate(i,c,false).also{_b=it}.root
    override fun onViewCreated(v: View, s: Bundle?){
        adapter = PostAdapter { id -> startActivity(Intent(requireContext(), PostFormActivity::class.java).putExtra("id", id)) }
        b.rv.layoutManager = LinearLayoutManager(requireContext()); b.rv.adapter = adapter
        b.btnAdd.setOnClickListener { startActivity(Intent(requireContext(), PostFormActivity::class.java)) }
        vm.posts.observe(viewLifecycleOwner) { adapter.submit(it) }
    }
    override fun onDestroyView(){_b=null;super.onDestroyView()}
}
