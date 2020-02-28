package com.reinventiva.sticket.ui.newticketnumber

import android.os.Bundle
import com.reinventiva.sticket.R
import com.reinventiva.sticket.ui.MyBaseActivity

class NewTicketNumberActivity: MyBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_ticket_number_activity)
        supportActionBar?.title = "Ticket Nuevo"
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, NewTicketNumberFragment())
                .commitNow()
        }
    }

}
