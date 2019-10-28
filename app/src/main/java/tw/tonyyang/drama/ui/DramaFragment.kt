package tw.tonyyang.drama.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_drama.*
import tw.tonyyang.drama.R
import tw.tonyyang.drama.model.Drama
import tw.tonyyang.drama.viewmodel.DramaViewModel

class DramaFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(DramaViewModel::class.java)
    }

    private val dramaAdapter by lazy {
        DramaAdapter(activity as Context).also {
            it.listener = dramaItemListener
        }
    }

    private val dramaItemListener = object : DramaAdapter.OnItemClickListener {

        override fun onItemClick(view: View, drama: Drama) {
            DramaFragmentDirections.actionDramaFragmentToDramaDetailFragment(drama = drama).let {
                view.findNavController().navigate(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_drama, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_drama_list.apply {
            setHasFixedSize(true)
            adapter = dramaAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(
                SeparatorDecoration.Builder(context)
                    .setMargin(16F, 0F)
                    .build()
            )
        }
        et_drama_filter.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val charString = s.toString()
                dramaAdapter.filter.filter(charString)
            }
        })
        viewModel.dramaListLiveData.observe(this, Observer {
            dramaAdapter.update(it)
        })
        viewModel.loadDramaList()
    }
}