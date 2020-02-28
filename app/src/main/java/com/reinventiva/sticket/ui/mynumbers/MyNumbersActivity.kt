package com.reinventiva.sticket.ui.mynumbers

import android.os.Bundle
import com.reinventiva.sticket.R
import com.reinventiva.sticket.ui.base.MyBaseActivity

class MyNumbersActivity: MyBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_numbers_activity)
        supportActionBar?.title = "Mis Números"
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MyNumbersFragment())
                .commitNow()
        }
    }

    fun refreshList() = (supportFragmentManager.fragments[0] as MyNumbersFragment).refreshList()
}
