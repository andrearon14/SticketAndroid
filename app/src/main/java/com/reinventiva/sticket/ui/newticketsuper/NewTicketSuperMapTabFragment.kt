package com.reinventiva.sticket.ui.newticketsuper

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.reinventiva.sticket.R
import com.reinventiva.sticket.model.LocationExtension
import com.reinventiva.sticket.model.PlaceViewModel
import com.reinventiva.sticket.model.toLatLng
import com.reinventiva.sticket.ui.newticketnumber.NewTicketNumberActivity

class NewTicketSuperMapTabFragment : Fragment(), OnMapReadyCallback {

    private lateinit var viewModel: PlaceViewModel
    private lateinit var map: GoogleMap
    private var lastClickMarkerId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.new_ticket_super_map_tab_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)

        val mapFragment = childFragmentManager.fragments[0]
        if (mapFragment is SupportMapFragment)
            mapFragment.getMapAsync(this)

        viewModel.list.observe(viewLifecycleOwner, Observer { list ->
            val builder = LatLngBounds.Builder()
            for (data in list) {
                LocationExtension.fromGxPosition(data.Position)?.toLatLng()?.let {
                    val marker = map.addMarker(MarkerOptions().position(it).title(data.Name))
                    marker.tag = data.Id
                    builder.include(it)
                }
            }
            viewModel.currentLocation?.let {
                builder.include(it.toLatLng())
            }
            val bounds = builder.build()
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, MAP_PADDING))
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.isMyLocationEnabled = true
        map.setOnInfoWindowClickListener {
            val intent = Intent(context, NewTicketNumberActivity::class.java)
            context!!.startActivity(intent)
        }
        map.setOnMapClickListener {
            lastClickMarkerId = null
        }
        map.setOnMarkerClickListener {
            if (lastClickMarkerId == it.tag as Int) {
                val intent = Intent(context, NewTicketNumberActivity::class.java)
                context!!.startActivity(intent)
                lastClickMarkerId = null
                true
            } else {
                lastClickMarkerId = it.tag as Int
                false
            }
        }
    }

    companion object {
        private const val MAP_PADDING = 50 // pixels
    }
}
