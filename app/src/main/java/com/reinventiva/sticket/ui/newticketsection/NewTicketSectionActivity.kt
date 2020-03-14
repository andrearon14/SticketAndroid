package com.reinventiva.sticket.ui.newticketsection

import android.content.Intent
import android.os.Bundle
import com.reinventiva.sticket.R
import com.reinventiva.sticket.ui.base.MyBaseActivity
import com.reinventiva.sticket.ui.newticketnumber.NEW_TICKET_RESULT_CODE_HAS_NUMBERS
import com.reinventiva.sticket.ui.newticketnumber.NewTicketNumberActivity

internal const val NEW_TICKET_REQUEST_CODE = 113

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (/*requestCode == NEW_TICKET_REQUEST_CODE &&*/ resultCode == NEW_TICKET_RESULT_CODE_HAS_NUMBERS) {
            setResult(NEW_TICKET_RESULT_CODE_HAS_NUMBERS)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        if (NewTicketNumberActivity.fromNotificationHash != null)
            finish()
    }
}