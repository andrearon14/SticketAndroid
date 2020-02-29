package com.reinventiva.sticket.ui.newticketnumber

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.*
import com.reinventiva.sticket.data.Repository
import kotlinx.coroutines.launch
import org.json.JSONObject

class NewTicketNumberViewModel : ViewModel() {
    val list = MutableLiveData<List<NewTicketNumberData>>()

    init {
        viewModelScope.launch {
            refreshList()
        }
    }

    private suspend fun refreshList() {
        list.value = Repository.R.getAvailableNumbers()
    }

    fun refresh() = viewModelScope.launch {
        refreshList()
    }

    fun getTickets(sections: List<String>) = viewModelScope.launch {
        Repository.R.getTickets(sections)
        refreshList()
    }

    fun refreshOne(name: String, values: JSONObject) {
        list.value?.let {
            val isDelete = name == "Delete"
            val section = values["Section"] as String
            for (item in it) {
                if (item.Section.trimEnd() == section) {
                    if (isDelete)
                        item.HasNumber = false
                    else
                        item.Waiting = (values["Waiting"] as String).toInt()
                    Handler(Looper.getMainLooper()).post {
                        list.value = it
                    }
                    break
                }
            }
        }
    }
}
