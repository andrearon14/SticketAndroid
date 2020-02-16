package com.reinventiva.sticket.ui.newticketnumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.reinventiva.sticket.R

class NewTicketNumberActivity : AppCompatActivity() {

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
