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

    fun refreshOne(values: JSONObject) {
        list.value?.let {
            val section = values["Section"] as String
            val number = values["CurrentNumber"] as String
            for (item in it) {
                if (item.Section.trimEnd() == section) {
                    item.CurrentNumber = number
                    Handler(Looper.getMainLooper()).post {
                        list.value = it
                    }
                    break
                }
            }
        }
    }
}
