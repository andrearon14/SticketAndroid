package com.reinventiva.sticket.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.reinventiva.sticket.R
import com.reinventiva.sticket.data.Repository

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
    }

}
