
package com.tripmate.ui.plan
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tripmate.databinding.FragmentPlanBinding
import com.tripmate.vm.PlanViewModel
class PlanFragment: Fragment() {
    private var _b: FragmentPlanBinding? = null; private val b get() = _b!!
    private val vm: PlanViewModel by viewModels()
    private lateinit var adapter: TripAdapter
    override fun onCreateView(i: LayoutInflater,c: ViewGroup?,s: Bundle?)=FragmentPlanBinding.inflate(i,c,false).also{_b=it}.root
    override fun onViewCreated(v: View, s: Bundle?) {
        adapter = TripAdapter { id -> startActivity(Intent(requireContext(), TripFormActivity::class.java).putExtra("id", id)) }
        b.rv.layoutManager = LinearLayoutManager(requireContext()); b.rv.adapter = adapter
        b.btnAdd.setOnClickListener { startActivity(Intent(requireContext(), TripFormActivity::class.java)) }
        vm.items.observe(viewLifecycleOwner) { adapter.submit(it) }
    }
    override fun onDestroyView(){_b=null;super.onDestroyView()}
}
