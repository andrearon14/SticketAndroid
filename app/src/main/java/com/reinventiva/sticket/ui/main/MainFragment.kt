package com.reinventiva.sticket.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.reinventiva.sticket.R
import com.reinventiva.sticket.model.MyNumbersViewModel
import com.reinventiva.sticket.ui.myinformation.MyInformationActivity
import com.reinventiva.sticket.ui.mynumbers.MyNumbersActivity
import com.reinventiva.sticket.ui.newticketchoose.NewTicketChooseActivity
import com.reinventiva.sticket.ui.newticketnumber.NewTicketNumberActivity
import kotlinx.android.synthetic.main.main_fragment.*

private const val MY_REQUEST_CODE = 111

class MainFragment: Fragment() {

    private lateinit var viewModel: MyNumbersViewModel
    private var placeId : Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyNumbersViewModel::class.java)

        viewModel.list.observe(viewLifecycleOwner, Observer {
            placeId = it.firstOrNull()?.PlaceId
        })

        val newTicket = View.OnClickListener {
            val intent = if (placeId == null) {
                Intent(context, NewTicketChooseActivity::class.java)
            } else {
                placeId?.let {
                    Intent(context, NewTicketNumberActivity::class.java)
                        .putExtra(NewTicketNumberActivity.EXTRA_PLACE, it)
                }
            }
            startActivityForResult(intent, MY_REQUEST_CODE)
        }
        imageNewTicket.setOnClickListener(newTicket)
        buttonNewTicket.setOnClickListener(newTicket)

        val myNumbers = View.OnClickListener {
            val intent = Intent(context, MyNumbersActivity::class.java)
            startActivityForResult(intent, MY_REQUEST_CODE)
        }
        imageMyNumbers.setOnClickListener(myNumbers)
        buttonMyNumbers.setOnClickListener(myNumbers)

        val myProfile = View.OnClickListener {
            val intent = Intent(context, MyInformationActivity::class.java)
            startActivity(intent)
        }
        imageMyProfile.setOnClickListener(myProfile)
        buttonMyProfile.setOnClickListener(myProfile)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MY_REQUEST_CODE)
            viewModel.refresh()
    }
}
