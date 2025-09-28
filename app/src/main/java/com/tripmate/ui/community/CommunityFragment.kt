
package com.tripmate.ui.community
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tripmate.data.AppDatabase
import com.tripmate.data.Post
import com.tripmate.databinding.FragmentCommunityBinding
import com.tripmate.vm.CommunityViewModel
import kotlinx.coroutines.launch

class CommunityFragment: Fragment(){
    private var _b: FragmentCommunityBinding?=null; private val b get()=_b!!
    private val vm: CommunityViewModel by viewModels()
    private lateinit var adapter: PostAdapter
    private val dao by lazy { AppDatabase.get(requireContext()).postDao() }
    override fun onCreateView(i: LayoutInflater,c: ViewGroup?,s: Bundle?)=FragmentCommunityBinding.inflate(i,c,false).also{_b=it}.root
    override fun onViewCreated(v: View, s: Bundle?){
        adapter = PostAdapter(
            onEdit = { post -> startActivity(Intent(requireContext(), PostFormActivity::class.java).putExtra("id", post.id)) },
            onDelete = { post ->
                lifecycleScope.launch {
                    dao.delete(post)
                    Toast.makeText(requireContext(), "Post deleted", Toast.LENGTH_SHORT).show()
                }
            }
        )
        b.rv.layoutManager = LinearLayoutManager(requireContext()); b.rv.adapter = adapter
        b.btnAdd.setOnClickListener { startActivity(Intent(requireContext(), PostFormActivity::class.java)) }
        vm.posts.observe(viewLifecycleOwner) { adapter.submit(it) }
    }
    override fun onDestroyView(){_b=null;super.onDestroyView()}
}
