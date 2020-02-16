package com.reinventiva.sticket.ui.newticketsuper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.reinventiva.sticket.R

class NewTicketSuperMapTabFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.new_ticket_super_map_tab_fragment, container, false)
    }

}
