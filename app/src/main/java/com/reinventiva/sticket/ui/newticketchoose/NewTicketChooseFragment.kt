package com.reinventiva.sticket.ui.newticketchoose

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.reinventiva.sticket.R
import com.reinventiva.sticket.ui.newticketsection.NewTicketSectionActivity
import com.reinventiva.sticket.ui.newticketsuper.NEW_TICKET_REQUEST_CODE
import com.reinventiva.sticket.ui.newticketsuper.NewTicketSuperActivity
import kotlinx.android.synthetic.main.new_ticket_choose_fragment.*

class NewTicketChooseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.new_ticket_choose_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        buttonChooseSuper.setOnClickListener {
            val intent = Intent(context, NewTicketSuperActivity::class.java)
            activity?.startActivityForResult(intent, NEW_TICKET_REQUEST_CODE)
        }

        buttonChooseTime.setOnClickListener {
            val intent = Intent(context, NewTicketSectionActivity::class.java)
            activity?.startActivityForResult(intent, NEW_TICKET_REQUEST_CODE)
        }
    }
}