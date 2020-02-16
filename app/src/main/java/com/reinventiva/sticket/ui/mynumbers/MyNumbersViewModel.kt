package com.reinventiva.sticket.ui.mynumbers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyNumbersViewModel : ViewModel() {
    val list = MutableLiveData<List<MyNumbersData>>(
        arrayListOf(
            MyNumbersData("Devoto", "Carniceria", "A - 12"),
            MyNumbersData("Devoto", "Rotiseria", "B - 8")
        )
    )
}
