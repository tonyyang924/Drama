package tw.tonyyang.drama.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_drama_detail.*
import tw.tonyyang.drama.R
import tw.tonyyang.drama.viewmodel.DramaDetailViewModel

class DramaDetailFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(DramaDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_drama_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.dramaLiveData.observe(this, Observer { drama ->
            drama?.let {
                it.thumb.let { url ->
                    Glide.with(activity as Context)
                        .load(url).into(iv_drama_cover)
                }
                tv_drama_name.text = it.name
                mrb_rating.rating = it.rating.toFloat()
                tv_rating.text = it.rating.toString()
                tv_drama_total_views.text = getString(R.string.drama_total_views_title, it.totalViews)
                tv_drama_created_at.text = getString(R.string.drama_created_at_title, it.createdAt)
            }
        })

        val idStr = activity?.intent?.data?.getQueryParameter("id")
        if (idStr?.isNotEmpty() == true) {
            viewModel.loadDrama(idStr.toInt())
            return
        }
        arguments?.let {
            val dramaId = DramaDetailFragmentArgs.fromBundle(it).dramaId
            val drama = DramaDetailFragmentArgs.fromBundle(it).drama
            if (drama != null) {
                viewModel.loadDrama(drama)
            }
            if (dramaId != 0) {
                viewModel.loadDrama(dramaId)
            }
        }
    }
}