package com.reinventiva.sticket.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import com.onesignal.OneSignal
import com.reinventiva.sticket.R
import com.reinventiva.sticket.data.Repository
import com.reinventiva.sticket.ui.base.MyBaseActivity
import com.reinventiva.sticket.ui.mynumbers.MyNumbersActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainActivity: MyBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        supportActionBar?.title = "S-ticket"
        Repository.R = Repository(this)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        // Iniciar OneSignal
        OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .setNotificationReceivedHandler { notification ->
                val activity = myApp.currentActivity
                if (activity is MyBaseActivity) {
                    if (notification.payload.title != null) {
                        // Notificación recibida, mostrar mensaje
                        val snackbar = Snackbar.make(
                            activity.coordinatorLayout,
                            notification.payload.title + "\n" + notification.payload.body,
                            Snackbar.LENGTH_INDEFINITE
                        )
                        val params = snackbar.view.layoutParams as CoordinatorLayout.LayoutParams
                        params.gravity = Gravity.TOP
                        snackbar.view.layoutParams = params
                        snackbar.show()
                    } else if (notification.payload.additionalData != null) {
                        // Notificación silenciosa recibida, actualizar pantallas
                        val jsonObject = notification.payload.additionalData
                        val values = (jsonObject["da"] as JSONObject)["p"] as JSONObject
                        activity.updateNotification(values)
                    }
                }
            }
            .setNotificationOpenedHandler { result ->
                // Notificación abierta, ir al panel Mis Números
                val activity = myApp.currentActivity
                if (activity is MyNumbersActivity) {
                    activity.refreshList()
                } else {
                    val intent = Intent(this@MainActivity, MyNumbersActivity::class.java)
                    startActivity(intent)
                }
            }
            .unsubscribeWhenNotificationsAreDisabled(true)
            .init()

        registerDevice(OneSignal.getPermissionSubscriptionState().subscriptionStatus.userId)
        OneSignal.addSubscriptionObserver {
            if (it.from.userId != it.to.userId)
                registerDevice(it.to.userId)
        }
    }

    private fun registerDevice(deviceToken: String?) {
        if (deviceToken != null) {
            GlobalScope.launch {
                Repository.R.registerDevice(deviceToken)
            }
        }
    }
}
