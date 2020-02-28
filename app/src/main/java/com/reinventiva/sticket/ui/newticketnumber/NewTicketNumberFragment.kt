package com.reinventiva.sticket.ui.newticketnumber

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.reinventiva.sticket.R
import kotlinx.android.synthetic.main.new_ticket_number_fragment.*
import org.json.JSONObject

class NewTicketNumberFragment: Fragment() {

    private lateinit var viewModel: NewTicketNumberViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.new_ticket_number_fragment, container, false)
    }

    @SuppressLint("HardwareIds")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewTicketNumberViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.list.observe(viewLifecycleOwner, Observer {
            recyclerView.adapter = NewTicketNumberRecyclerAdapter(context!!, it)
            swipeRefresh.isRefreshing = false
        })

        button.setOnClickListener {
            val adapter = recyclerView.adapter
            if (adapter is NewTicketNumberRecyclerAdapter ) {
                val sections = adapter.list
                    .filterIndexed { index, _ -> adapter.selectedPositions.contains(index) }
                    .map { d -> d.Section }
                viewModel.getTickets(sections)
            }
        }

        swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    fun refreshValues(values: JSONObject) = viewModel.refreshOne(values)
}
