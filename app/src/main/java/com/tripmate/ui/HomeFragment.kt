
package com.tripmate.ui
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tripmate.databinding.FragmentHomeBinding
import com.tripmate.vm.HomeViewModel
class HomeFragment: Fragment() {
    private var _b: FragmentHomeBinding? = null; private val b get() = _b!!
    private val vm: HomeViewModel by viewModels()
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?) = FragmentHomeBinding.inflate(i,c,false).also{_b=it}.root
    override fun onViewCreated(v: View, s: Bundle?) {
        b.rvPopular.layoutManager = LinearLayoutManager(requireContext())
        vm.seed()

        // Show top 5 community posts by rating at the top
        val postAdapter = com.tripmate.ui.community.PostAdapter(
            onEdit = {},
            onDelete = {}
        )
        b.rvPopular.adapter = postAdapter
        vm.topCommunityPosts.observe(viewLifecycleOwner) { postAdapter.submit(it) }
    }
    override fun onDestroyView(){_b=null;super.onDestroyView()}
}
