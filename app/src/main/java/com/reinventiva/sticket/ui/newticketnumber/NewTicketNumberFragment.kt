package com.reinventiva.sticket.ui.newticketnumber

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings.Secure
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reinventiva.sticket.R
import com.reinventiva.sticket.data.Repository
import kotlinx.android.synthetic.main.new_ticket_number_fragment.*
import kotlinx.android.synthetic.main.new_ticket_super_list_tab_fragment.recyclerView
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class NewTicketNumberFragment : Fragment() {

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
            val adapter = NewTicketNumberRecyclerAdapter(context!!, it)
            recyclerView.adapter = adapter
        })

        button.setOnClickListener {
            val adapter = recyclerView.adapter
            if (adapter is NewTicketNumberRecyclerAdapter ) {
                val sections = adapter.list
                    .filterIndexed { index, _ -> adapter.selectedPositions.contains(index) }
                    .map { d -> d.Section }
                viewModel.viewModelScope.launch {
                    viewModel.getTickets(sections)
                }
            }
        }
    }
}
