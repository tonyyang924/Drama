package tw.tonyyang.drama.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_drama_detail.*
import tw.tonyyang.drama.R

class DramaDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_drama_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val drama = arguments?.let {
            DramaDetailFragmentArgs.fromBundle(it).drama
        }
        drama?.let {
            it.thumb.let {
                Glide.with(activity as Context)
                    .load(it).into(iv_drama_cover)
            }
            tv_drama_name.text = it.name
            mrb_rating.rating = it.rating.toFloat()
            tv_rating.text = it.rating.toString()
            tv_drama_total_views.text = getString(R.string.drama_total_views_title, it.totalViews)
            tv_drama_created_at.text = getString(R.string.drama_created_at_title, it.createdAt)
        }

    }
}