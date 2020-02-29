package com.reinventiva.sticket.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewTicketSuperViewModel : ViewModel() {

    val list = MutableLiveData<List<NewTicketSuperData>>(
        arrayListOf(
            NewTicketSuperData(
                "Supersol",
                "40m"
            ),
            NewTicketSuperData(
                "Devoto",
                "150m"
            )
        )
    )
}