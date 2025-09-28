
package com.tripmate.ui.plan

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tripmate.data.AppDatabase
import com.tripmate.data.TripItem
import com.tripmate.databinding.FragmentPlanBinding
import com.tripmate.vm.PlanViewModel
import kotlinx.coroutines.launch

class PlanFragment: Fragment() {
    private var _b: FragmentPlanBinding? = null; private val b get() = _b!!
    private val vm: PlanViewModel by viewModels()
    private lateinit var adapter: TripAdapter
    private val dao by lazy { AppDatabase.get(requireContext()).tripDao() }
    override fun onCreateView(i: LayoutInflater,c: ViewGroup?,s: Bundle?)=FragmentPlanBinding.inflate(i,c,false).also{_b=it}.root
    override fun onViewCreated(v: View, s: Bundle?) {
        adapter = TripAdapter(
            onEdit = { item -> startActivity(Intent(requireContext(), TripFormActivity::class.java).putExtra("id", item.id)) },
            onDelete = { item ->
                lifecycleScope.launch {
                    dao.delete(item)
                    Toast.makeText(requireContext(), "Trip item deleted", Toast.LENGTH_SHORT).show()
                }
            }
        )
        b.rv.layoutManager = LinearLayoutManager(requireContext()); b.rv.adapter = adapter
        b.btnAdd.setOnClickListener { startActivity(Intent(requireContext(), TripFormActivity::class.java)) }
        vm.items.observe(viewLifecycleOwner) { adapter.submit(it) }
    }
    override fun onDestroyView(){_b=null;super.onDestroyView()}
}
