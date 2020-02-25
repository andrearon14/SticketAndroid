package com.reinventiva.sticket.ui.newticketnumber

import androidx.lifecycle.*
import com.reinventiva.sticket.data.Repository
import kotlinx.coroutines.launch

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

    fun getTickets(sections: List<String>) = viewModelScope.launch {
        Repository.R.getTickets(sections)
        refreshList()
    }
}
