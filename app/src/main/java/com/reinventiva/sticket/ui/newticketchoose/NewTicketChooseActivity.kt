package com.reinventiva.sticket.ui.newticketchoose

import android.content.Intent
import android.os.Bundle
import com.reinventiva.sticket.R
import com.reinventiva.sticket.ui.base.MyBaseActivity
import com.reinventiva.sticket.ui.newticketnumber.NEW_TICKET_RESULT_CODE_HAS_NUMBERS
import com.reinventiva.sticket.ui.newticketnumber.NewTicketNumberActivity

internal const val NEW_TICKET_REQUEST_CODE = 112

class NewTicketChooseActivity: MyBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_ticket_choose_activity)
        supportActionBar?.title = "Ticket Nuevo"
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, NewTicketChooseFragment())
                .commitNow()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEW_TICKET_REQUEST_CODE && resultCode == NEW_TICKET_RESULT_CODE_HAS_NUMBERS) {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        if (NewTicketNumberActivity.fromNotificationHash != null)
            finish()
    }
}
