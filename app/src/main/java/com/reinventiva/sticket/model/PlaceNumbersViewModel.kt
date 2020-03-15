package com.reinventiva.sticket.model

import androidx.lifecycle.*
import com.reinventiva.sticket.data.Repository
import kotlinx.coroutines.launch

class PlaceNumbersViewModel(private val placeId: Int) : NumbersViewModel() {

    init {
        refresh()
    }

    override suspend fun refreshList() {
        list.value = Repository.R.getPlaceNumbers(placeId)
    }

    fun takeTickets(sections: List<String>) = viewModelScope.launch {
        Repository.R.takeTickets(placeId, sections)
        refreshList()
    }
}
