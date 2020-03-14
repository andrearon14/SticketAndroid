package com.reinventiva.sticket.data

data class DataGoogleDistance(
    val destination_addresses: List<String>,
    val origin_addresses: List<String>,
    val rows: List<DataElements>,
    val status: String
)

data class DataElements(
    val elements: List<DataElement>
)

data class DataElement(
    val distance: DataValue,
    val duration: DataValue,
    val status: String
)

data class DataValue(
    val text: String,
    val value: Int
)