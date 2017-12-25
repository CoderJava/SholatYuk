package com.codepolitan.sholatyuk.network.api

import com.codepolitan.sholatyuk.BuildConfig
import com.codepolitan.sholatyuk.model.DataJadwalSholat
import com.codepolitan.sholatyuk.model.DataKota
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import okhttp3.OkHttpClient
import okhttp3.Request
import java.text.SimpleDateFormat
import java.util.*

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

    fun getPrayerScheduleData(id: Int): Deferred<DataJadwalSholat> {
        return async(CommonPool) {
            var url = BuildConfig.BASE_URL + BuildConfig.API_KEY + "/jadwal-sholat"
            val strDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
            url += "?idk=$id"
            url += url.let {
                val strMonth = strDate.substring(5, 5 + 2)
                val strYear = strDate.substring(0, 0 + 4)
                "&bln=$strMonth&thn=$strYear"
            }

            val request = Request.Builder()
                    .url(url)
                    .build()
            val response = okhttpClient.newCall(request).execute()
            val dataPrayerSchedule = object : TypeToken<DataJadwalSholat>() {}.type
            Gson().fromJson<DataJadwalSholat>(response.body()!!.string(), dataPrayerSchedule)
        }
    }

}