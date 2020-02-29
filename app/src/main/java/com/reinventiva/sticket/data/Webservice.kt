package com.reinventiva.sticket.data

import retrofit2.http.Body
import retrofit2.http.POST

interface Webservice {
    @POST("${REST}GetAvailableNumbers")
    suspend fun getAvailableNumbers(@Body data: DataDeviceIn): DataAvailableNumbersOut

    @POST("${REST}GetTickets")
    suspend fun getTickets(@Body data: DataTicketsIn)

    @POST("${REST}GetMyNumbers")
    suspend fun getMyNumbers(@Body data: DataDeviceIn): DataMyNumbersOut

    @POST("${REST}ReleaseTickets")
    suspend fun releaseTickets(@Body data: DataTicketsIn)

    @POST("${REST}NotificationsRegistrationHandler")
    suspend fun registerDevice(@Body data: DataDeviceRegistrationIn)

    @POST("${REST}GetMyInformation")
    suspend fun getMyInformation(@Body data: DataDeviceIn): DataMyInformationOut

    @POST("${REST}SetMyInformation")
    suspend fun setMyInformation(@Body data: DataMyInformationIn)

    companion object {
        private const val REST = "/Id169255290677041e3d4e2c55f705da5b/rest/"
    }
}