package com.reinventiva.sticket.ui.newticketsuper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.reinventiva.sticket.R
import com.reinventiva.sticket.model.PlaceData
import com.reinventiva.sticket.model.PlaceViewModel
import kotlinx.android.synthetic.main.new_ticket_super_list_tab_fragment.recyclerView
import kotlinx.android.synthetic.main.new_ticket_super_list_tab_fragment.swipeRefresh

/**
 * A placeholder fragment containing a simple view.
 */
class NewTicketSuperListTabFragment : Fragment() {

    private lateinit var viewModel: PlaceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.new_ticket_super_list_tab_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(context)

        swipeRefresh.isRefreshing = true
        viewModel.list.observe(viewLifecycleOwner, Observer<List<PlaceData>> {
            recyclerView.adapter = NewTicketSuperListRecyclerAdapter(context!!, it)
            swipeRefresh.isRefreshing = false
        })

        swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }
}
