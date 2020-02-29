package com.reinventiva.sticket.model

data class TicketNumberData(
    val Section: String,
    var HasTicket: Boolean,
    val TicketNumber: Int,
    val TicketTime: String,
    var CurrentNumber: Int,
    var LastNumber: Int
)
