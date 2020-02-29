package com.reinventiva.sticket.model

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.*
import com.reinventiva.sticket.data.Repository
import kotlinx.coroutines.launch
import org.json.JSONObject

class TicketNumberViewModel : ViewModel() {
    val list = MutableLiveData<List<TicketNumberData>>()

    init {
        viewModelScope.launch {
            refreshList()
        }
    }

    private suspend fun refreshList() {
        list.value = Repository.R.getNumbers()
    }

    fun refresh() = viewModelScope.launch {
        refreshList()
    }

    fun getTickets(sections: List<String>) = viewModelScope.launch {
        Repository.R.getTickets(sections)
        refreshList()
    }

    fun releaseTickets(sections: List<String>) = viewModelScope.launch {
        Repository.R.releaseTickets(sections)
        refreshList()
    }

    fun refreshOne(name: String, values: JSONObject) {
        list.value?.let {
            val isDelete = name == "Delete"
            val section = values["Section"] as String
            for (item in it) {
                if (item.Section.trimEnd() == section) {
                    if (isDelete) {
                        item.HasTicket = false
                    } else {
                        item.CurrentNumber = (values["CurrentNumber"] as String).toInt()
                        item.LastNumber = (values["LastNumber"] as String).toInt()
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
