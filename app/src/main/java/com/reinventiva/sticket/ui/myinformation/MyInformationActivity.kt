package com.reinventiva.sticket.ui.myinformation

import android.os.Bundle
import com.reinventiva.sticket.R
import com.reinventiva.sticket.ui.base.MyBaseActivity

class MyInformationActivity: MyBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_information_activity)
        supportActionBar?.title = "Mis Datos"
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MyInformationFragment())
                .commitNow()
        }
    }

}