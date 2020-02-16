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
import kotlinx.android.synthetic.main.new_ticket_super_list_tab_fragment.*

/**
 * A placeholder fragment containing a simple view.
 */
class NewTicketSuperListTabFragment : Fragment() {

    private lateinit var viewModel: NewTicketSuperViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.new_ticket_super_list_tab_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewTicketSuperViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.list.observe(viewLifecycleOwner, Observer<List<NewTicketSuperData>> {
            recyclerView.adapter =
                NewTicketSuperRecyclerAdapter(
                    context!!,
                    it
                )
        })
    }
}
