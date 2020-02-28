package com.reinventiva.sticket.ui.newticketsuper

import android.os.Bundle
import com.reinventiva.sticket.R
import com.reinventiva.sticket.ui.base.MyBaseActivity
import kotlinx.android.synthetic.main.new_ticket_super_activity.*

class NewTicketSuperActivity : MyBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_ticket_super_activity)
        supportActionBar?.title = "Ticket Nuevo"
        view_pager.adapter = NewTicketSuperPagerAdapter(this, supportFragmentManager)
        tabs.setupWithViewPager(view_pager)
    }

}
