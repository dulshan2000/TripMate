
package com.tripmate.ui.community
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tripmate.R
import com.tripmate.data.Post
class PostAdapter(private val onClick:(Int)->Unit): RecyclerView.Adapter<PostAdapter.VH>(){
    private var items: List<Post> = emptyList()
    fun submit(list: List<Post>){ items=list; notifyDataSetChanged() }
    class VH(v: View): RecyclerView.ViewHolder(v){ val t: TextView=v.findViewById(R.id.tvTitle); val s: TextView=v.findViewById(R.id.tvSub) }
    override fun onCreateViewHolder(p: ViewGroup, vt:Int)=VH(LayoutInflater.from(p.context).inflate(R.layout.item_post,p,false))
    override fun getItemCount()=items.size
    override fun onBindViewHolder(h: VH, i:Int){ val it=items[i]; h.t.text=it.title; h.s.text="${it.location} • ${it.stars} ★"; h.itemView.setOnClickListener{onClick(it.id)} }
}
