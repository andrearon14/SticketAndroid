package com.reinventiva.sticket.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.onesignal.OneSignal
import com.reinventiva.sticket.R
import com.reinventiva.sticket.data.Repository
import com.reinventiva.sticket.ui.MyBaseActivity
import com.reinventiva.sticket.ui.mynumbers.MyNumbersActivity
import com.reinventiva.sticket.ui.newticketnumber.NewTicketNumberActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

        // OneSignal Initialization
        OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .setNotificationReceivedHandler { notification ->
                Snackbar.make(window.decorView.rootView, "REC " + notification.payload.title!!, Snackbar.LENGTH_LONG).show()
            }
            .setNotificationOpenedHandler { result ->
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
