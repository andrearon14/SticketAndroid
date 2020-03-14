package com.reinventiva.sticket.ui.newticketsection

import android.os.Bundle
import com.reinventiva.sticket.R
import com.reinventiva.sticket.ui.base.MyBaseActivity

class NewTicketSectionActivity : MyBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_ticket_section_activity)
        supportActionBar?.title = "Ticket Nuevo"
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, NewTicketSectionFragment())
                .commitNow()
        }
    }
}