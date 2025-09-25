
package com.tripmate.ui.plan
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tripmate.R
import com.tripmate.data.TripItem
class TripAdapter(private val onClick:(Int)->Unit): RecyclerView.Adapter<TripAdapter.VH>(){
    private var items: List<TripItem> = emptyList()
    fun submit(list: List<TripItem>){ items=list; notifyDataSetChanged() }
    class VH(v: View): RecyclerView.ViewHolder(v){ val t: TextView=v.findViewById(R.id.tvTitle); val s: TextView=v.findViewById(R.id.tvSub) }
    override fun onCreateViewHolder(p: ViewGroup, vt:Int)=VH(LayoutInflater.from(p.context).inflate(R.layout.item_trip,p,false))
    override fun getItemCount()=items.size
    override fun onBindViewHolder(h: VH, i:Int){ val it=items[i]; h.t.text="Day ${it.day} • Attraction #${it.attractionId}"; h.s.text="Time ${it.time}"; h.itemView.setOnClickListener{onClick(it.id)} }
}
