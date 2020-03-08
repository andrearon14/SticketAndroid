package com.reinventiva.sticket.model

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.json.JSONObject

abstract class NumbersViewModel : ViewModel() {
    val list = MutableLiveData<List<NumberData>>()

    protected abstract suspend fun refreshList()

    fun refresh() = viewModelScope.launch {
        refreshList()
    }

    fun refreshOne(name: String, values: JSONObject) {
        list.value?.let {
            val isDelete = name == "Delete"
            val placeId = (values["PlaceId"] as String).toInt()
            val section = values["Section"] as String
            for (item in it) {
                if (item.PlaceId == placeId && item.Section.trimEnd() == section) {
                    if (isDelete) {
                        item.HasTicket = false
                    } else {
                        item.CurrentNumber = (values["CurrentNumber"] as String).toInt()
                        item.LastNumber = (values["LastNumber"] as String).toInt()
                        if (item.HasTicket && item.CurrentNumber > item.TicketNumber)
                            item.HasTicket = false
                    }
                    Handler(Looper.getMainLooper()).post {
                        if (list.value == it) // Si no fue actualizado por otro lado
                            list.value = it // Disparar los Observers
                    }
                    break
                }
            }
        }
    }
}