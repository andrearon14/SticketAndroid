package com.reinventiva.sticket.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reinventiva.sticket.data.Repository
import kotlinx.coroutines.launch

class SectionsViewModel : ViewModel() {
    val list = MutableLiveData<List<SectionData>>()

    init {
        refresh()
    }

    fun refresh() = viewModelScope.launch {
        refreshList()
    }

    suspend fun refreshList() {
        list.value = Repository.R.getSections()
    }
}