package com.reinventiva.sticket.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlaceNumberViewModelFactory(private val placeId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return PlaceNumbersViewModel(placeId) as T
    }
}