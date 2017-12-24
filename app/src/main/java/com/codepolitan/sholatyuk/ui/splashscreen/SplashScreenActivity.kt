package com.codepolitan.sholatyuk.ui.splashscreen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.codepolitan.sholatyuk.R
import com.codepolitan.sholatyuk.db.DatabaseHelper
import com.codepolitan.sholatyuk.experimental.Android
import com.codepolitan.sholatyuk.network.api.ShalatClient
import kotlinx.coroutines.experimental.launch

class SplashScreenActivity : AppCompatActivity() {

    private val TAG = javaClass.simpleName
    private val databaseHelper by lazy {
        DatabaseHelper(
                context = this@SplashScreenActivity,
                name = DatabaseHelper.DATABASE_NAME,
                factory = null,
                version = DatabaseHelper.DATABASE_VERSION
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        doLoadData()
    }

    private fun doLoadData() {
        launch(Android) {
            val itemCountDataCityLocal = databaseHelper.getDataCity()
            val resultDataKota = ShalatClient.getCityData().await()
            if (itemCountDataCityLocal == resultDataKota.count) {
                // todo: do something in here
            } else {
                databaseHelper.insertDataCity(resultDataKota.data)
            }

        }
    }
}
