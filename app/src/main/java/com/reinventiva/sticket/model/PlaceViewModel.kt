package com.reinventiva.sticket.model

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reinventiva.sticket.data.Repository
import kotlinx.coroutines.launch

class PlaceViewModel : ViewModel() {
    val currentLocation : Location? get() = lastLocation
    val list = MutableLiveData<List<PlaceData>>()

    init {
        refresh()
    }

    private suspend fun refreshList(location: Location) {
        val currentPosition = location.toGxPosition()
        val dataList = Repository.R.getPlaces(currentPosition)
        updateDistance(dataList, location)
        list.value = dataList
    }

    private fun updateDistance(dataList: List<PlaceData>, location: Location) {
        for (data in dataList) {
            val distance = location.distanceTo(LocationExtension.fromGxPosition(data.Position))
            data.Distance = (distance / 10).toInt() * 10
        }
    }

    fun updateLocation(location: Location) {
        lastLocation = location
        if (list.value == null) {
            refresh()
        } else {
            list.value?.let {
                updateDistance(it, location)
                list.value = it
            }
        }
    }

    fun refresh() = viewModelScope.launch {
        lastLocation?.let { refreshList(it) }
    }

    companion object {
        private var lastLocation: Location? = null
    }
}