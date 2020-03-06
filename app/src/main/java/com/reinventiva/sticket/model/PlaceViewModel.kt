package com.reinventiva.sticket.model

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reinventiva.sticket.data.Repository
import kotlinx.coroutines.launch

class PlaceViewModel : ViewModel() {
    val list = MutableLiveData<List<PlaceData>>()

    init {
        viewModelScope.launch {
            refreshList()
        }
    }

    private suspend fun refreshList() {
        val currentPosition = lastLocation?.toGxPosition() ?: ""
        val dataList = Repository.R.getPlaces(currentPosition)
        lastLocation?.let { updateDistance(dataList, it) }
        list.value = dataList
    }

    private fun updateDistance(dataList: List<PlaceData>, location: Location) {
        for (data in dataList) {
            val distance = location.distanceTo(LocationExtension.fromGxPosition(data.Position))
            data.Distance = (distance / 10).toInt() * 10
        }
    }

    fun updateDistance(location: Location) {
        list.value?.let {
            updateDistance(it, location)
            list.value = it
        }
        lastLocation = location
    }

    fun refresh() = viewModelScope.launch {
        refreshList()
    }

    companion object {
        private var lastLocation: Location? = null
    }
}