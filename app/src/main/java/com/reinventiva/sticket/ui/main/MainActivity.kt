package com.reinventiva.sticket.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.onesignal.OneSignal
import com.reinventiva.sticket.R
import com.reinventiva.sticket.data.Repository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

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
