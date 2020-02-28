package com.reinventiva.sticket.ui.mynumbers

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
import com.reinventiva.sticket.ui.newticketnumber.NewTicketNumberActivity
import kotlinx.android.synthetic.main.my_numbers_fragment.*
import org.json.JSONObject

private const val NEW_TICKET_REQUEST_CODE = 111

class MyNumbersFragment: Fragment() {

    private lateinit var viewModel: MyNumbersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.my_numbers_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyNumbersViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.list.observe(viewLifecycleOwner, Observer {
            recyclerView.adapter = MyNumbersRecyclerAdapter(context!!, it)
            swipeRefresh.isRefreshing = false
        })

        buttonAdd.setOnClickListener {
            val intent = Intent(context, NewTicketNumberActivity::class.java)
            startActivityForResult(intent, NEW_TICKET_REQUEST_CODE)
        }

        buttonRemove.setOnClickListener {
            val adapter = recyclerView.adapter
            if (adapter is MyNumbersRecyclerAdapter) {
                val sections = adapter.list
                    .filterIndexed { index, _ -> adapter.selectedPositions.contains(index) }
                    .map { d -> d.Section }
                viewModel.releaseTickets(sections)
            }
        }

        swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEW_TICKET_REQUEST_CODE) {
            viewModel.refresh()
        }
    }

    fun refreshList() = viewModel.refresh()
    fun refreshValues(values: JSONObject) = viewModel.refreshOne(values)
}
