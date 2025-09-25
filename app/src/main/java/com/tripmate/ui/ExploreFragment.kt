
package com.tripmate.ui
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tripmate.databinding.FragmentExploreBinding
import com.tripmate.vm.ExploreViewModel
class ExploreFragment: Fragment() {
    private var _b: FragmentExploreBinding? = null; private val b get() = _b!!
    private val vm: ExploreViewModel by viewModels()
    override fun onCreateView(i: LayoutInflater,c: ViewGroup?,s: Bundle?)=FragmentExploreBinding.inflate(i,c,false).also{_b=it}.root
    override fun onViewCreated(v: View, s: Bundle?) {
        b.rvAttractions.layoutManager = LinearLayoutManager(requireContext())
        b.etSearch.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){}
            override fun afterTextChanged(s: Editable?){}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int){ vm.search(s?.toString()?: "")}
        })
    }
    override fun onDestroyView(){_b=null;super.onDestroyView()}
}
