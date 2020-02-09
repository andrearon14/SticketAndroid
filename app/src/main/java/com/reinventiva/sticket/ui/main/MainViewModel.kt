package com.reinventiva.sticket.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

class MainViewModel : ViewModel() {
    private val repository = Repository()

    val first = liveData(Dispatchers.IO) {
        val s = repository.getData()
        emit(s)
    }
}
