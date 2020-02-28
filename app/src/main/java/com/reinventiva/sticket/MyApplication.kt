package com.reinventiva.sticket

import android.app.Activity
import android.app.Application

class MyApplication : Application() {
    var currentActivity: Activity? = null
}