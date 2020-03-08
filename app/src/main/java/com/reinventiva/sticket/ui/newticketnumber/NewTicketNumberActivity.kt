package com.reinventiva.sticket.ui.newticketnumber

import android.os.Bundle
import com.reinventiva.sticket.R
import com.reinventiva.sticket.ui.base.MyBaseActivity
import org.json.JSONObject

const val NEW_TICKET_RESULT_CODE_HAS_NUMBERS = 1

class NewTicketNumberActivity: MyBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_ticket_number_activity)
        supportActionBar?.title = "Ticket Nuevo"
        if (savedInstanceState == null) {
            val placeId = intent.getIntExtra(EXTRA_PLACE, 0)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, NewTicketNumberFragment(placeId))
                .commitNow()
        }
    }

    private val fragment get() = supportFragmentManager.fragments[0] as NewTicketNumberFragment

    override fun updateNotification(name: String, values: JSONObject) = fragment.refreshValues(name, values)

    companion object {
        const val EXTRA_PLACE = "Place"
    }
}
