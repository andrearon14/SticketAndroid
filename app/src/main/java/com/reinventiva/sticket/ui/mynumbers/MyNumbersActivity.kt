package com.reinventiva.sticket.ui.mynumbers

import android.os.Bundle
import com.reinventiva.sticket.R
import com.reinventiva.sticket.ui.base.MyBaseActivity
import org.json.JSONObject

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

    private val fragment get() = supportFragmentManager.fragments[0] as MyNumbersFragment

    fun refreshList() = fragment.refreshList()

    override fun updateNotification(values: JSONObject) = fragment.refreshValues(values)
}
