package com.reinventiva.sticket.geo

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Location
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

class GeoRepository(val context: Context) {

    private val geofencingClient = LocationServices.getGeofencingClient(context)
    private val mapIntent = HashMap<String, PendingIntent>()

    fun add(key: String, location: Location, intent: PendingIntent) {
        mapIntent.set(key, intent)
        val geofence = buildGeofence(key, location)
        geofencingClient
            .addGeofences(buildGeofencingRequest(geofence), geofencePendingIntent)
            .addOnFailureListener {
                //GeofenceErrorMessages.getErrorString(context, it)
                Toast.makeText(context, "Error con notificación de llegada", Toast.LENGTH_LONG).show()
            }
    }

    fun remove(key: String) {
        geofencingClient.removeGeofences(listOf(key))
    }

    private fun buildGeofence(key: String, location: Location): Geofence {
        return Geofence.Builder()
            .setRequestId(key)
            .setCircularRegion(
                location.latitude,
                location.longitude,
                GEOFENCE_RADIUS_IN_METERS)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .setExpirationDuration(GEOFENCE_EXPIRATION_IN_MILLISECONDS)
            .build()
    }

    private fun buildGeofencingRequest(geofence: Geofence): GeofencingRequest {
        return GeofencingRequest.Builder()
            .setInitialTrigger(0)
            .addGeofences(listOf(geofence))
            .build()
    }

    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun getIntent(key: String?) =  mapIntent.get(key)

    companion object {
        lateinit var R: GeoRepository
        const val GEOFENCE_RADIUS_IN_METERS = 100F
        const val GEOFENCE_EXPIRATION_IN_MILLISECONDS = 3600000L // 1 hora
    }
}