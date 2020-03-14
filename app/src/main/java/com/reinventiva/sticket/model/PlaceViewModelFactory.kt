package com.reinventiva.sticket.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlaceViewModelFactory(private val sections: List<String>) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return PlaceViewModel(sections) as T
    }
}