package com.reinventiva.sticket.ui.newticketnumber

import android.os.Bundle
import com.reinventiva.sticket.R
import com.reinventiva.sticket.ui.base.MyBaseActivity
import org.json.JSONObject

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

    private val fragment get() = supportFragmentManager.fragments[0] as NewTicketNumberFragment

    override fun updateNotification(name: String, values: JSONObject) = fragment.refreshValues(name, values)
}
