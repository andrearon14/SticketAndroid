package com.reinventiva.sticket.ui.newticketsuper

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.reinventiva.sticket.R
import com.reinventiva.sticket.model.PlaceViewModel
import com.reinventiva.sticket.ui.base.MyBaseActivity
import com.reinventiva.sticket.ui.newticketnumber.NEW_TICKET_RESULT_CODE_HAS_NUMBERS
import com.reinventiva.sticket.ui.newticketnumber.NewTicketNumberActivity
import kotlinx.android.synthetic.main.new_ticket_super_activity.*

private const val PERMISSION_ID = 1
private const val NEW_TICKET_REQUEST_CODE = 111

class NewTicketSuperActivity : MyBaseActivity() {

    private lateinit var viewModel: PlaceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_ticket_super_activity)
        supportActionBar?.title = "Ticket Nuevo"

        viewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)

        if (checkPermissions())
            load()
        else
            requestPermissions()
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Granted. Start getting the location information
                load()
            } else {
                finish()
            }
        }
    }

    private fun load() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                if (location != null)
                    viewModel.updateLocation(location)
            }

        view_pager.adapter = NewTicketSuperPagerAdapter(this, supportFragmentManager, viewModel)
        tabs.setupWithViewPager(view_pager)
    }

    fun showNewTicketActivity(placeId: Int) {
        val intent = Intent(this, NewTicketNumberActivity::class.java)
            .putExtra(NewTicketNumberActivity.EXTRA_PLACE, placeId)
        startActivityForResult(intent, NEW_TICKET_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEW_TICKET_REQUEST_CODE && resultCode == NEW_TICKET_RESULT_CODE_HAS_NUMBERS)
            finish()
    }
}
