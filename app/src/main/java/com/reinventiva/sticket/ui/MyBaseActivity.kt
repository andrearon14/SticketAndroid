package com.reinventiva.sticket.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.reinventiva.sticket.MyApplication

open class MyBaseActivity : AppCompatActivity() {
    protected lateinit var myApp: MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myApp = this.applicationContext as MyApplication
    }

    override fun onResume() {
        super.onResume()
        myApp.currentActivity = this
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
}