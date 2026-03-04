package com.ixafrica.netscan.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface NetScanApi {
    @GET("json/{ip}")
    suspend fun getIpInfo(@Path("ip") ip: String): IpInfoResponse

    companion object {
        private const val BASE_URL = "https://ipwhois.app/"

        fun create(): NetScanApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NetScanApi::class.java)
        }
    }
}