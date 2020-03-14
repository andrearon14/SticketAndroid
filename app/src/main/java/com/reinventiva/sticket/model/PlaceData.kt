package com.reinventiva.sticket.model

data class PlaceData(
    val Id: Int,
    val Name: String,
    val Position: String,
    val Waiting: Int, // segundos

    // no viene de GX
    var Distance: Int = 0,
    var GoogleDistance: String? = null,
    var GoogleTravelTime: Int? = null
)
