package com.reinventiva.sticket.ui.main

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Webservice {
    @POST("/TestRest/rest/TestRestAPI")
    suspend fun getData(): TestData
}