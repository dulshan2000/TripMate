
package com.tripmate.ui.plan
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tripmate.R
import com.tripmate.data.TripItem

import android.widget.PopupMenu

class TripAdapter(
    private val onEdit: (TripItem) -> Unit,
    private val onDelete: (TripItem) -> Unit
): RecyclerView.Adapter<TripAdapter.VH>(){
    private var items: List<TripItem> = emptyList()
    fun submit(list: List<TripItem>){ items=list; notifyDataSetChanged() }
    class VH(v: View): RecyclerView.ViewHolder(v){
        val t: TextView = v.findViewById(R.id.tvTitle)
        val s: TextView = v.findViewById(R.id.tvSub)
    }
    override fun onCreateViewHolder(p: ViewGroup, vt:Int) = VH(LayoutInflater.from(p.context).inflate(R.layout.item_trip, p, false))
    override fun getItemCount() = items.size
    override fun onBindViewHolder(h: VH, i: Int) {
        val item = items[i]
        h.t.text = "Day ${item.day} • Attraction #${item.attractionId}"
        h.s.text = "Time ${item.time}"
        h.itemView.setOnClickListener { onEdit(item) }
        h.itemView.setOnLongClickListener { v ->
            PopupMenu(v.context, v).apply {
                inflate(R.menu.item_popup_menu)
                setOnMenuItemClickListener { menuItem ->
                    when(menuItem.itemId) {
                        R.id.action_edit -> { onEdit(item); true }
                        R.id.action_delete -> { onDelete(item); true }
                        else -> false
                    }
                }
                show()
            }
            true
        }
    }
}
