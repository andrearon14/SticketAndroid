package com.reinventiva.sticket.ui.newticketsuper

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
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.reinventiva.sticket.R
import com.reinventiva.sticket.geo.LocationExtension
import com.reinventiva.sticket.model.PlaceViewModel
import com.reinventiva.sticket.geo.toLatLng
import com.reinventiva.sticket.model.PlaceData

class NewTicketSuperMapTabFragment : Fragment(), OnMapReadyCallback {

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
        val viewModel = ViewModelProvider(activity!!).get(PlaceViewModel::class.java)

        val mapFragment = childFragmentManager.fragments[0]
        if (mapFragment is SupportMapFragment)
            mapFragment.getMapAsync(this)

        viewModel.list.observe(viewLifecycleOwner, Observer { list ->
            map.clear()
            val builder = LatLngBounds.Builder()
            var first = true
            for (data in list) {
                LocationExtension.fromGxPosition(data.Position)?.toLatLng()?.let {
                    val marker = map.addMarker(MarkerOptions().position(it).title(data.Name))
                    marker.tag = data
                    if (first)
                        first = false
                    else if (data.GoogleDistance != null && viewModel.sections.isNotEmpty())
                        marker.alpha = .5f
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
            showNewTicketActivity(it)
        }
        map.setOnMarkerClickListener {
            val clickMarkerId = (it.tag as PlaceData).Id
            if (lastClickMarkerId == clickMarkerId) {
                showNewTicketActivity(it)
                lastClickMarkerId = null
                true
            } else {
                lastClickMarkerId = clickMarkerId
                false
            }
        }
    }

    private fun showNewTicketActivity(marker: Marker) {
        (activity as NewTicketSuperActivity).showNewTicketActivity(marker.tag as PlaceData)
    }

    companion object {
        private const val MAP_PADDING = 60 // pixels
    }
}
