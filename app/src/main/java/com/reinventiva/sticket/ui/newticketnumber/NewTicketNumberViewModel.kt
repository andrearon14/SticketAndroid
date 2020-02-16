package com.reinventiva.sticket.ui.newticketnumber

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewTicketNumberViewModel : ViewModel() {
    val list = MutableLiveData<List<NewTicketNumberData>>(
        arrayListOf(
            NewTicketNumberData("Carniceria", "A - 10", 5),
            NewTicketNumberData("Panaderia", "C - 7", 8),
            NewTicketNumberData("Rotiseria", "B - 2", 2)
        )
    )
}
