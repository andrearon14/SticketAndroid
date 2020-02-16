package com.reinventiva.sticket.ui.newticketsuper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.reinventiva.sticket.R
import kotlinx.android.synthetic.main.new_ticket_super_activity.*

class NewTicketSuperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_ticket_super_activity)
        supportActionBar?.title = "Ticket Nuevo"
        view_pager.adapter = NewTicketSuperPagerAdapter(this, supportFragmentManager)
        tabs.setupWithViewPager(view_pager)
    }

}
