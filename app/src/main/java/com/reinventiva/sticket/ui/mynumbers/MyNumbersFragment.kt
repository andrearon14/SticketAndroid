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
import com.reinventiva.sticket.model.MyNumbersViewModel
import com.reinventiva.sticket.ui.newticketchoose.NewTicketChooseActivity
import com.reinventiva.sticket.ui.newticketnumber.NewTicketNumberActivity
import com.reinventiva.sticket.ui.newticketnumber.NewTicketNumberActivity.Companion.EXTRA_PLACE
import kotlinx.android.synthetic.main.my_numbers_fragment.*
import org.json.JSONObject

private const val NEW_TICKET_REQUEST_CODE = 111

class MyNumbersFragment : Fragment() {

    private lateinit var viewModel: MyNumbersViewModel
    private var placeId: Int = 0

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

        swipeRefresh.isRefreshing = true
        viewModel.list.observe(viewLifecycleOwner, Observer { data ->
            noNumbers.visibility = if (data.count { it.HasTicket } == 0) View.VISIBLE else View.GONE
            recyclerView.adapter = MyNumbersRecyclerAdapter(context!!, data.filter { it.HasTicket }) { buttonRemove.isEnabled = it }
            data.firstOrNull()?.let { placeId = it.PlaceId }
            swipeRefresh.isRefreshing = false
        })

        buttonAdd.setOnClickListener {
            val intent = if (placeId == 0) {
                Intent(context, NewTicketChooseActivity::class.java)
            } else {
                Intent(context, NewTicketNumberActivity::class.java)
                    .putExtra(EXTRA_PLACE, placeId)
            }
            startActivityForResult(intent, NEW_TICKET_REQUEST_CODE)
        }

        buttonRemove.setOnClickListener {
            val adapter = recyclerView.adapter
            if (adapter is MyNumbersRecyclerAdapter) {
                swipeRefresh.isRefreshing = true
                for (entry in adapter.selectedPlaceSections)
                    viewModel.releaseTickets(entry.key, entry.value)
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
    fun refreshValues(name: String, values: JSONObject) = viewModel.refreshOne(name, values)
}
