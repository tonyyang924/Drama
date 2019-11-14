package tw.tonyyang.drama.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_drama.view.*
import tw.tonyyang.drama.R
import tw.tonyyang.drama.model.Drama
import java.util.*
import kotlin.collections.ArrayList

class DramaAdapter :
    RecyclerView.Adapter<DramaAdapter.ViewHolder>(), Filterable {

    interface OnItemClickListener {
        fun onItemClick(view: View, drama: Drama)
    }

    var listener: OnItemClickListener? = null

    private val mLock = Any()

    var mLastFilterConstraint = ""

    private val originList = mutableListOf<Drama>()

    private var filterList = mutableListOf<Drama>()

    fun update(dramaList: List<Drama>) {
        synchronized(mLock) {
            this.originList.clear()
            this.originList.addAll(dramaList)
        }

        notifyAdapterDataSetChanged()
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
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_drama, parent, false))

    override fun getItemCount() = filterList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filterList[position])
    }

    private fun notifyAdapterDataSetChanged() {
        if (mLastFilterConstraint.isNotEmpty()) {
            filter.filter(mLastFilterConstraint)
        } else {
            synchronized(mLock) {
                filterList = ArrayList(originList)
            }
            notifyDataSetChanged()
        }
    }

    override fun getFilter() = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val charString = constraint.toString().also {
                mLastFilterConstraint = it
            }
            filterList = if (charString.isEmpty()) {
                originList
            } else {
                mutableListOf<Drama>().apply {
                    originList.forEach {
                        if (it.name.toLowerCase(Locale.getDefault()).contains(charString)) {
                            this.add(it)
                        }
                    }
                }
            }
            return FilterResults().apply {
                values = filterList
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            @Suppress("UNCHECKED_CAST")
            filterList = results?.values as MutableList<Drama>

            notifyDataSetChanged()
        }
    }
}