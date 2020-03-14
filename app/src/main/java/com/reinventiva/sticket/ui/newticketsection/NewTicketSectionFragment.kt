package com.reinventiva.sticket.ui.newticketsection

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.reinventiva.sticket.R
import com.reinventiva.sticket.model.SectionsViewModel
import com.reinventiva.sticket.ui.newticketnumber.NewTicketNumberActivity
import com.reinventiva.sticket.ui.newticketsuper.NEW_TICKET_REQUEST_CODE
import com.reinventiva.sticket.ui.newticketsuper.NewTicketSuperActivity
import kotlinx.android.synthetic.main.my_numbers_fragment.recyclerView
import kotlinx.android.synthetic.main.my_numbers_fragment.swipeRefresh
import kotlinx.android.synthetic.main.new_ticket_section_fragment.*

class NewTicketSectionFragment : Fragment() {

    private lateinit var viewModel: SectionsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.new_ticket_section_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SectionsViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(context)

        swipeRefresh.isRefreshing = true
        viewModel.list.observe(viewLifecycleOwner, Observer {
            recyclerView.adapter = NewTicketSectionRecyclerAdapter(context!!, it) { button.isEnabled = it }
            swipeRefresh.isRefreshing = false
        })

        button.setOnClickListener {
            val adapter = recyclerView.adapter
            if (adapter is NewTicketSectionRecyclerAdapter) {
                val intent = Intent(context, NewTicketSuperActivity::class.java)
                    .putExtra(NewTicketNumberActivity.EXTRA_SECTIONS, adapter.selectedSections.toTypedArray())
                startActivityForResult(intent, NEW_TICKET_REQUEST_CODE)
            }
        }

        swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }
}