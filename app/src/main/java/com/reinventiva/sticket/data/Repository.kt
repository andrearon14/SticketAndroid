package com.reinventiva.sticket.data

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.provider.Settings
import android.widget.Toast
import com.reinventiva.sticket.model.MyInformationData
import com.reinventiva.sticket.model.NumberData
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class Repository(context: Context) {

    @SuppressLint("HardwareIds")
    private val androidId: String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request: Request = chain.request()
            val response: Response = chain.proceed(request)
            if (response.code() != 200) {
                Handler(context.mainLooper).post {
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show()
                }
                response.newBuilder().code(200).build()
            } else {
                response
            }
        }
        .build()

    private val client = Retrofit.Builder()
        .baseUrl("https://apps5.genexus.com/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build().create(Webservice::class.java)

    private val apiKey = context.resources.getString(context.resources.getIdentifier("google_maps_key", "string", context.packageName))

    suspend fun getMyNumbers() = client.getMyNumbers(DataDeviceIn(androidId)).List
    suspend fun getPlaceNumbers(placeId: Int): List<NumberData> = client.getPlaceNumbers(DataNumbersIn(androidId, placeId)).List
    suspend fun getPlaces(currentPosition: String, sections: List<String>, distance: Int) = client.getPlaces(DataPlacesIn(currentPosition, sections, distance)).List
    suspend fun takeTickets(placeId: Int, sections: List<String>) = client.takeTickets(DataTicketsIn(androidId, placeId, sections))
    suspend fun releaseTickets(placeId: Int, sections: List<String>) = client.releaseTickets(DataTicketsIn(androidId, placeId, sections))
    suspend fun registerDevice(deviceToken: String) = client.registerDevice(DataDeviceRegistrationIn(androidId, deviceToken))
    suspend fun getMyInformation() = client.getMyInformation(DataDeviceIn(androidId)).Data
    suspend fun setMyInformation(data: MyInformationData) = client.setMyInformation(DataMyInformationIn(androidId, data))
    suspend fun getSections() = client.getSections().List
    suspend fun getGoogleDistance(origins: String, destinations: String) = client.getGoogleDistance(origins, destinations, apiKey)

    companion object {
        lateinit var R: Repository
    }
}