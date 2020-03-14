package com.reinventiva.sticket.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Webservice {
    @POST("${REST}GetMyNumbers")
    suspend fun getMyNumbers(@Body data: DataDeviceIn): DataNumbersOut

    @POST("${REST}GetPlaceNumbers")
    suspend fun getPlaceNumbers(@Body data: DataNumbersIn): DataNumbersOut

    @POST("${REST}GetPlaces")
    suspend fun getPlaces(@Body data: DataPlacesIn): DataPlacesOut

    @POST("${REST}TakeTickets")
    suspend fun takeTickets(@Body data: DataTicketsIn)

    @POST("${REST}ReleaseTickets")
    suspend fun releaseTickets(@Body data: DataTicketsIn)

    @POST("${REST}NotificationsRegistrationHandler")
    suspend fun registerDevice(@Body data: DataDeviceRegistrationIn)

    @POST("${REST}GetMyInformation")
    suspend fun getMyInformation(@Body data: DataDeviceIn): DataMyInformationOut

    @POST("${REST}SetMyInformation")
    suspend fun setMyInformation(@Body data: DataMyInformationIn)

    @POST("${REST}GetSections")
    suspend fun getSections(): DataSectionsOut

    @GET("https://maps.googleapis.com/maps/api/distancematrix/json")
    suspend fun getGoogleDistance(@Query("origins") origins: String,
                                  @Query("destinations") destinations: String,
                                  @Query("key") key: String): DataGoogleDistance

    companion object {
        private const val REST = "/Id169255290677041e3d4e2c55f705da5b/rest/"
    }
}