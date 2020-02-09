package com.reinventiva.sticket.ui.main

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class Repository {

    var client = Retrofit.Builder()
        .baseUrl("https://apps5.genexus.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build().create(Webservice::class.java)

    //simplified version of the retrofit call that comes from support with coroutines
    //Note that this does NOT handle errors, to be added
    suspend fun getData() = client.getData()
}