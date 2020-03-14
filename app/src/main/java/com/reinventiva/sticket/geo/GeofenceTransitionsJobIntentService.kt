package com.reinventiva.sticket.geo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.reinventiva.sticket.Constants
import com.reinventiva.sticket.R

class GeofenceTransitionsJobIntentService : JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            val errorMessage = GeofenceErrorMessages.getErrorString(this, geofencingEvent.errorCode)
            Log.e(Constants.TAG, errorMessage)
            return
        }
        handleEvent(geofencingEvent)
    }

    private fun handleEvent(event: GeofencingEvent) {
        if (event.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            val requestId = event.triggeringGeofences[0].requestId
            sendNotification(GeoRepository.R.getIntent(requestId))

            for (geofence in event.triggeringGeofences)
                GeoRepository.R.remove(geofence.requestId)
        }
    }

    private fun sendNotification(intent: PendingIntent?) {
        createNotificationChannel()
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.icono_ticket_nuevo)
            .setContentTitle("Ya puedes emitir tu ticket")
            .setPriority(Notification.PRIORITY_HIGH)
            .setAutoCancel(true)
        if (intent != null)
            builder.setContentIntent(intent)
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_HIGH)
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "ArrivoDestino"
        private const val NOTIFICATION_ID = 346
        private const val JOB_ID = 573
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, GeofenceTransitionsJobIntentService::class.java, JOB_ID, intent)
        }
    }
}
