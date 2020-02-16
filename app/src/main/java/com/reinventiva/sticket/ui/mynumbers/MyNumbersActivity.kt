package com.reinventiva.sticket.ui.mynumbers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.reinventiva.sticket.R

class MyNumbersActivity : AppCompatActivity() {

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

}
