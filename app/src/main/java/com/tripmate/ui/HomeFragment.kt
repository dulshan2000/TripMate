
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
        b.rvFeatured.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        b.rvPopular.layoutManager = LinearLayoutManager(requireContext())
        vm.seed()
    }
    override fun onDestroyView(){_b=null;super.onDestroyView()}
}
