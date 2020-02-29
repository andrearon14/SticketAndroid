package com.reinventiva.sticket.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.reinventiva.sticket.R
import com.reinventiva.sticket.ui.myinformation.MyInformationActivity
import com.reinventiva.sticket.ui.mynumbers.MyNumbersActivity
import com.reinventiva.sticket.ui.newticketnumber.NewTicketNumberActivity
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment: Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val newTicket = View.OnClickListener {
            val intent = Intent(context, NewTicketNumberActivity::class.java)
            startActivity(intent)
        }
        imageNewTicket.setOnClickListener(newTicket)
        buttonNewTicket.setOnClickListener(newTicket)

        val myNumbers = View.OnClickListener {
            val intent = Intent(context, MyNumbersActivity::class.java)
            startActivity(intent)
        }
        imageMyNumbers.setOnClickListener(myNumbers)
        buttonMyNumbers.setOnClickListener(myNumbers)

        val myprofile = View.OnClickListener {
            val intent = Intent(context, MyInformationActivity::class.java)
            startActivity(intent)
        }
        imageMyProfile.setOnClickListener(myprofile)
        buttonMyProfile.setOnClickListener(myprofile)
    }
}
