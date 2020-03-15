package com.reinventiva.sticket.ui.newticketsuper

import android.Manifest
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.reinventiva.sticket.R
import com.reinventiva.sticket.Utils
import com.reinventiva.sticket.geo.GeoRepository
import com.reinventiva.sticket.geo.LocationExtension
import com.reinventiva.sticket.model.PlaceData
import com.reinventiva.sticket.model.PlaceViewModel
import com.reinventiva.sticket.model.PlaceViewModelFactory
import com.reinventiva.sticket.ui.base.MyBaseActivity
import com.reinventiva.sticket.ui.newticketnumber.NEW_TICKET_RESULT_CODE_HAS_NUMBERS
import com.reinventiva.sticket.ui.newticketnumber.NewTicketNumberActivity
import com.reinventiva.sticket.ui.newticketnumber.NewTicketNumberFragment
import kotlinx.android.synthetic.main.new_ticket_super_activity.*

private const val PERMISSION_ID = 1
internal const val NEW_TICKET_REQUEST_CODE = 111

class NewTicketSuperActivity : MyBaseActivity() {

    private lateinit var viewModel: PlaceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_ticket_super_activity)
        supportActionBar?.title = "Ticket Nuevo"

        val sections = intent.getStringArrayExtra(NewTicketNumberActivity.EXTRA_SECTIONS)?.toList() ?: emptyList()
        viewModel = ViewModelProvider(this, PlaceViewModelFactory(sections)).get(PlaceViewModel::class.java)

        val needsBackground = sections.isNotEmpty() &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
        if (checkPermissions(needsBackground))
            load()
        else
            requestPermissions(needsBackground)
    }

    private fun checkPermissions(needsBackground: Boolean): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false
        }
        if (needsBackground &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false
        }

        return true
    }

    private fun requestPermissions(needsBackground: Boolean) {
        ActivityCompat.requestPermissions(
            this,
            if (needsBackground)
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            else
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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

        view_pager.adapter = NewTicketSuperPagerAdapter(this, supportFragmentManager)
        tabs.setupWithViewPager(view_pager)
    }

    fun showNewTicketActivity(place: PlaceData) {
        val intent = Intent(this, NewTicketNumberActivity::class.java)
            .putExtra(NewTicketNumberActivity.EXTRA_PLACE, place.Id)
            .putExtra(NewTicketNumberActivity.EXTRA_SECTIONS, viewModel.sections.toTypedArray())
        startActivityForResult(intent, NEW_TICKET_REQUEST_CODE)
        if (viewModel.sections.isNotEmpty()) {
            val intentGeo = Intent(this, NewTicketNumberActivity::class.java)
                .putExtra(NewTicketNumberActivity.EXTRA_PLACE, place.Id)
                .putExtra(NewTicketNumberActivity.EXTRA_SECTIONS, viewModel.sections.toTypedArray())
                .putExtra(NewTicketNumberActivity.EXTRA_FROM_NOTIFICATION, true)
            LocationExtension.fromGxPosition(place.Position)?.let {
                val pendingIntent = PendingIntent.getActivity(this, NEW_TICKET_REQUEST_CODE, intentGeo, FLAG_ONE_SHOT)
                GeoRepository.R.add(place.Name, it, pendingIntent)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEW_TICKET_REQUEST_CODE && resultCode == NEW_TICKET_RESULT_CODE_HAS_NUMBERS) {
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
