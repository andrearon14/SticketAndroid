package com.reinventiva.sticket.data

data class DataDeviceRegistrationIn(
    val DeviceId: String,
    val DeviceToken: String,
    val DeviceType: Int = 1, // Android
    val DeviceName: String = "Android"
)