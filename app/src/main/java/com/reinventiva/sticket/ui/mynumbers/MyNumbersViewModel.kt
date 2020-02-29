package com.reinventiva.sticket.ui.mynumbers

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reinventiva.sticket.data.Repository
import kotlinx.coroutines.launch
import org.json.JSONObject

class MyNumbersViewModel : ViewModel() {
    val list = MutableLiveData<List<MyNumbersData>>()

    init {
        viewModelScope.launch {
            refreshList()
        }
    }

    private suspend fun refreshList() {
        list.value = Repository.R.getMyNumbers()
    }

    fun refresh() = viewModelScope.launch {
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
                        val newList = it.toMutableList()
                        newList.remove(item)
                        Handler(Looper.getMainLooper()).post {
                            list.value = newList
                        }
                    } else {
                        item.CurrentNumber = values["CurrentNumber"] as String
                        Handler(Looper.getMainLooper()).post {
                            list.value = it
                        }
                    }
                    break
                }
            }
        }
    }
}
