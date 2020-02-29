package com.reinventiva.sticket.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.reinventiva.sticket.MyApplication
import com.reinventiva.sticket.R
import org.json.JSONObject

open class MyBaseActivity : AppCompatActivity() {
    protected lateinit var myApp: MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myApp = this.applicationContext as MyApplication
    }

    override fun onResume() {
        super.onResume()
        myApp.currentActivity = this
        coordinatorLayout = findViewById(R.id.container)
    }

    override fun onPause() {
        clearReferences()
        super.onPause()
    }

    override fun onDestroy() {
        clearReferences()
        super.onDestroy()
    }

    private fun clearReferences() {
        val currActivity = myApp.currentActivity
        if (this == currActivity)
            myApp.currentActivity = null
    }

    open fun updateNotification(name: String, values: JSONObject) { }

    lateinit var coordinatorLayout: CoordinatorLayout
}
