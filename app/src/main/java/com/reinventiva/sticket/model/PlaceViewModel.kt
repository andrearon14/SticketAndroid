package com.reinventiva.sticket.model

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reinventiva.sticket.data.Repository
import kotlinx.coroutines.launch

class PlaceViewModel(val sections: List<String>) : ViewModel() {
    val currentLocation : Location? get() = lastLocation
    val list = MutableLiveData<List<PlaceData>>()

    init {
        refresh()
    }

    private suspend fun refreshList(location: Location) {
        val currentPosition = location.toGxPosition()
        var dataList = Repository.R.getPlaces(currentPosition, sections, PLACE_DISTANCE_CLOSE)
        if (dataList.isEmpty()) {
            dataList = Repository.R.getPlaces(currentPosition, sections, PLACE_DISTANCE_MEDIUM)
            if (dataList.isEmpty()) {
                dataList = Repository.R.getPlaces(currentPosition, sections, PLACE_DISTANCE_FAR)
            }
        }
        list.value = updateDistance(dataList, location)
        dataList = list.value!!
        val sortedDataList = updateGoogleDistance(dataList, location)
        if (list.value == dataList)
            list.value = sortedDataList
    }

    private fun updateDistance(dataList: List<PlaceData>, location: Location): List<PlaceData> {
        for (data in dataList) {
            val distance = location.distanceTo(LocationExtension.fromGxPosition(data.Position))
            data.Distance = (distance / 10).toInt() * 10
        }
        return dataList.sortedBy { it.Distance }
    }

    private suspend fun updateGoogleDistance(dataList: List<PlaceData>, location: Location): List<PlaceData> {
        if (dataList.isEmpty())
            return dataList

        val origins = "${location.latitude},${location.longitude}" // "-34.89684,-56.0983983"
        var destinations = ""
        for (data in dataList) {
            LocationExtension.fromGxPosition(data.Position)?.let {
                if (destinations.isNotEmpty())
                    destinations += "|"
                destinations += "${it.latitude},${it.longitude}"
            }
        }
        if (destinations.isNotEmpty()) {
            val distance = Repository.R.getGoogleDistance(origins, destinations)
            for ((index, element) in distance.rows[0].elements.withIndex()) {
                dataList[index].GoogleDistance = element.distance.text
                dataList[index].GoogleTravelTime = element.duration.value
            }
            val sorted = dataList.sortedBy { it.GoogleTravelTime!! + it.Waiting }
            return sorted // sin variable no funciona
        }
        return dataList
    }

    fun updateLocation(location: Location) {
        lastLocation = location
        if (list.value == null) {
            refresh()
        } else {
            list.value?.let {
                list.value = updateDistance(it, location)
                viewModelScope.launch {
                    val dataList = list.value!!
                    val sortedDataList = updateGoogleDistance(dataList, location)
                    if (list.value == dataList)
                        list.value = sortedDataList
                }
            }
        }
    }

    fun refresh() = viewModelScope.launch {
        lastLocation?.let { refreshList(it) }
    }

    companion object {
        private var lastLocation: Location? = null
        private const val PLACE_DISTANCE_CLOSE = 1000 // metros
        private const val PLACE_DISTANCE_MEDIUM = 4000 // metros
        private const val PLACE_DISTANCE_FAR = 10000 // metros
    }
}