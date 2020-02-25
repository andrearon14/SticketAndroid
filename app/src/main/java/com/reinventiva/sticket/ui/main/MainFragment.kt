package com.reinventiva.sticket.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.reinventiva.sticket.ui.newticketsuper.NewTicketSuperActivity
import com.reinventiva.sticket.R
import com.reinventiva.sticket.ui.mynumbers.MyNumbersActivity
import com.reinventiva.sticket.ui.newticketnumber.NewTicketNumberActivity
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        buttonNewTicket.setOnClickListener {
            val intent = Intent(context, NewTicketNumberActivity::class.java)
            startActivity(intent)
        }

        buttonMyNumbers.setOnClickListener {
            val intent = Intent(context, MyNumbersActivity::class.java)
            startActivity(intent)
        }
    }

}