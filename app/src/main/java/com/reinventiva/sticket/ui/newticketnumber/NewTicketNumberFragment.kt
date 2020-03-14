package com.reinventiva.sticket.ui.newticketnumber

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.reinventiva.sticket.R
import com.reinventiva.sticket.model.PlaceNumbersViewModel
import com.reinventiva.sticket.model.PlaceNumbersViewModelFactory
import kotlinx.android.synthetic.main.new_ticket_number_fragment.*
import org.json.JSONObject

class NewTicketNumberFragment(private val placeId: Int, private var sections: List<String>?): Fragment() {

    private lateinit var viewModel: PlaceNumbersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.new_ticket_number_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, PlaceNumbersViewModelFactory(placeId)).get(PlaceNumbersViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(context)

        swipeRefresh.isRefreshing = true
        viewModel.list.observe(viewLifecycleOwner, Observer {
            recyclerView.adapter = NewTicketNumberRecyclerAdapter(context!!, it, sections) { button.isEnabled = it }
            sections = null // Una vez solamente
            if (it.count { it.HasTicket } > 0)
                activity?.setResult(NEW_TICKET_RESULT_CODE_HAS_NUMBERS)
            swipeRefresh.isRefreshing = false
        })

        button.setOnClickListener {
            val adapter = recyclerView.adapter
            if (adapter is NewTicketNumberRecyclerAdapter ) {
                swipeRefresh.isRefreshing = true
                viewModel.takeTickets(adapter.selectedSections)
            }
        }

        swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    fun refreshValues(name: String, values: JSONObject) = viewModel.refreshOne(name, values)
}
