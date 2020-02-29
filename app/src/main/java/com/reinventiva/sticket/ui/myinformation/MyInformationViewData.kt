package com.reinventiva.sticket.ui.myinformation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reinventiva.sticket.data.Repository
import kotlinx.coroutines.launch

class MyInformationViewModel : ViewModel() {
    val information = MutableLiveData<MyInformationData>()

    init {
        viewModelScope.launch {
            refresh()
        }
    }

    private suspend fun refresh() {
        information.value = Repository.R.getMyInformation()
    }

    suspend fun save(data: MyInformationData) {
        Repository.R.setMyInformation(data)
        information.value = data
    }

    fun refreshData() = viewModelScope.launch {
        refresh()
    }
}
