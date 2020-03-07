package com.reinventiva.sticket.model

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import java.math.BigDecimal

class LocationExtension {
    companion object {
        fun fromGxPosition(geopoint: String): Location? {
            // Format is "POINT(<longitude> <latitude>)"
            if (geopoint.startsWith("POINT")) {
                var unwrapped = geopoint.substring(5).trim()
                if (unwrapped.startsWith("(") && unwrapped.endsWith(")")) {
                    unwrapped = unwrapped.substring(1, unwrapped.length - 1).trim()
                    val lonLat = unwrapped.split(' ')
                    if (lonLat.size == 2) {
                        val location = Location("")
                        location.latitude = lonLat[1].trim().toDouble()
                        location.longitude = lonLat[0].trim().toDouble()
                        return location
                    }
                }
            }
            return null
        }
    }
}

private const val DECIMAL_PLACES = 7 // Maximum precision.
fun Location.toGxPosition() : String {
    // Format is "POINT (<longitude> <latitude>)"
    fun coordinateToString(value: Double): String =
        BigDecimal.valueOf(value).setScale(DECIMAL_PLACES, BigDecimal.ROUND_HALF_EVEN).toPlainString()
    return "POINT (${coordinateToString(longitude)} ${coordinateToString(latitude)})"
}

fun Location.toLatLng() = LatLng(latitude, longitude)
