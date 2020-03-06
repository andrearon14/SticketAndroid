package com.reinventiva.sticket.ui.newticketsuper

import android.Manifest
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
import kotlinx.android.synthetic.main.new_ticket_super_activity.*

class NewTicketSuperActivity : MyBaseActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var viewModel: PlaceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_ticket_super_activity)
        supportActionBar?.title = "Ticket Nuevo"

        viewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)

        if (checkPermissions())
            loadCurrentLocation()
        else
            requestPermissions()

        view_pager.adapter = NewTicketSuperPagerAdapter(this, supportFragmentManager)
        tabs.setupWithViewPager(view_pager)
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
                loadCurrentLocation()
            }
        }
    }

    private fun loadCurrentLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                if (location != null)
                    viewModel.updateDistance(location)
            }
    }

    companion object {
        private const val PERMISSION_ID = 1
    }
}
