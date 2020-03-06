package com.reinventiva.sticket.data

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.provider.Settings
import android.widget.Toast
import com.reinventiva.sticket.model.MyInformationData
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

    suspend fun getNumbers() = client.getNumbers(DataDeviceIn(androidId)).List
    suspend fun takeTickets(sections: List<String>) = client.takeTickets(DataTicketsIn(androidId, sections))
    suspend fun releaseTickets(sections: List<String>) = client.releaseTickets(DataTicketsIn(androidId, sections))
    suspend fun registerDevice(deviceToken: String) = client.registerDevice(DataDeviceRegistrationIn(androidId, deviceToken))
    suspend fun getMyInformation() = client.getMyInformation(DataDeviceIn(androidId)).Data
    suspend fun setMyInformation(data: MyInformationData) = client.setMyInformation(DataMyInformationIn(androidId, data))

    companion object {
        lateinit var R: Repository
    }
}