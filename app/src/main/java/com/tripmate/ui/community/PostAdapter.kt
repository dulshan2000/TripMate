
package com.tripmate.ui.community
import android.view.*
import android.widget.TextView
import android.widget.ImageView
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.tripmate.R
import com.tripmate.data.Post

import android.widget.PopupMenu

class PostAdapter(
    private val onEdit: (Post) -> Unit,
    private val onDelete: (Post) -> Unit
): RecyclerView.Adapter<PostAdapter.VH>(){
    private var items: List<Post> = emptyList()
    fun submit(list: List<Post>){ items=list; notifyDataSetChanged() }
    class VH(v: View): RecyclerView.ViewHolder(v){
        val t: TextView = v.findViewById(R.id.tvTitle)
        val s: TextView = v.findViewById(R.id.tvSub)
        val img: ImageView = v.findViewById(R.id.ivImage)
    }
    override fun onCreateViewHolder(p: ViewGroup, vt:Int) = VH(LayoutInflater.from(p.context).inflate(R.layout.item_post, p, false))
    override fun getItemCount() = items.size
    override fun onBindViewHolder(h: VH, i: Int) {
        val item = items[i]
        h.t.text = item.title
        h.s.text = "${item.location} • ${item.stars} ★"
        if (item.imageUri != null) {
            try {
                h.img.setImageURI(Uri.parse(item.imageUri))
                h.img.visibility = View.VISIBLE
            } catch (e: SecurityException) {
                h.img.setImageDrawable(null)
                h.img.visibility = View.GONE
                android.util.Log.e("PostAdapter", "Permission denied for image URI", e)
            } catch (e: Exception) {
                h.img.setImageDrawable(null)
                h.img.visibility = View.GONE
                android.util.Log.e("PostAdapter", "Error setting image URI", e)
            }
        } else {
            h.img.setImageDrawable(null)
            h.img.visibility = View.GONE
        }
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
