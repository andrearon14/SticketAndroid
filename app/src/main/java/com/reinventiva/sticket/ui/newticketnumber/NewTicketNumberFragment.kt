package com.reinventiva.sticket.ui.newticketnumber

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.reinventiva.sticket.R
import kotlinx.android.synthetic.main.new_ticket_super_list_tab_fragment.*

class NewTicketNumberFragment : Fragment() {

    private lateinit var viewModel: NewTicketNumberViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.new_ticket_number_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewTicketNumberViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.list.observe(viewLifecycleOwner, Observer {
            val adapter = NewTicketNumberRecyclerAdapter(context!!, it)
            recyclerView.adapter = adapter
        })
    }

}
