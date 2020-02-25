package com.reinventiva.sticket.ui.mynumbers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reinventiva.sticket.data.Repository
import kotlinx.coroutines.launch

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
}
