package com.reinventiva.sticket.model

import androidx.lifecycle.*
import com.reinventiva.sticket.data.Repository
import kotlinx.coroutines.launch

class MyNumbersViewModel : NumbersViewModel() {

    init {
        refresh()
    }

    override suspend fun refreshList() {
        list.value = Repository.R.getMyNumbers()
    }

    fun releaseTickets(placeId: Int, sections: List<String>) = viewModelScope.launch {
        Repository.R.releaseTickets(placeId, sections)
        refreshList()
    }
}
