package com.codepolitan.sholatyuk.network.api

import com.codepolitan.sholatyuk.BuildConfig
import com.codepolitan.sholatyuk.model.DataKota
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Created by yudisetiawan on 12/23/17.
 */
object ShalatClient {

    private val TAG = javaClass.simpleName
    val okhttpClient = OkHttpClient()

    /**
     * Get City Data from API
     */
    fun getCityData(): Deferred<DataKota> {
        return async(CommonPool) {
            val request = Request.Builder()
                    .url(BuildConfig.BASE_URL + BuildConfig.API_KEY + "/jadwal-sholat/get-kota")
                    .build()
            val response = okhttpClient.newCall(request).execute()
            val dataKota = object : TypeToken<DataKota>() {}.type
            Gson().fromJson<DataKota>(response.body()!!.string(), dataKota)
        }
    }

}