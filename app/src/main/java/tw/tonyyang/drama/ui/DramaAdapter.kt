package tw.tonyyang.drama.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_drama.view.*
import tw.tonyyang.drama.R
import tw.tonyyang.drama.model.Drama

class DramaAdapter(private val context: Context) :
    RecyclerView.Adapter<DramaAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(view: View, drama: Drama)
    }

    var listener : OnItemClickListener? = null

    private val mInflater by lazy {
        LayoutInflater.from(context)
    }

    private val dramaList = mutableListOf<Drama>()

    fun update(dramaList: List<Drama>) {
        this.dramaList.clear()
        this.dramaList.addAll(dramaList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(drama: Drama) {
            Glide.with(itemView.context).load(drama.thumb).into(itemView.iv_drama_thumb)
            itemView.tv_drama_name.text = drama.name
            itemView.mrb_rating.rating = drama.rating.toFloat()
            itemView.tv_drama_created_at.text = drama.createdAt
            itemView.setOnClickListener {
                listener?.onItemClick(itemView, drama)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(mInflater.inflate(R.layout.item_drama, parent, false))

    override fun getItemCount() = dramaList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dramaList[position])
    }
}