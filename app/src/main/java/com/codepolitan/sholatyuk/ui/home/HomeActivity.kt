package com.codepolitan.sholatyuk.ui.home

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.codepolitan.sholatyuk.R
import com.codepolitan.sholatyuk.experimental.Android
import com.codepolitan.sholatyuk.model.Data
import com.codepolitan.sholatyuk.network.api.ShalatClient
import com.codepolitan.sholatyuk.ui.city.CityActivity
import com.codepolitan.sholatyuk.ui.schedule.PrayerScheduleActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.experimental.launch

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = javaClass.simpleName
    private val REQUEST_CODE_CITY = 210
    private var dataCity: Data? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initListener()
        initToolbar()
    }

    /**
     * @description Initialize toolbar
     */
    private fun initToolbar() {
        setSupportActionBar(toolbar_activity_home)
        supportActionBar?.title = getString(R.string.app_name)
    }

    /**
     * @description Initialize all listener view
     */
    private fun initListener() {
        text_view_value_location_city_activity_home.setOnClickListener(this)
        button_submit_activity_home.setOnClickListener(this)
    }

    /**
     * @description override listener on click
     * @param view {View} view for on click
     */
    override fun onClick(view: View?) {
        view?.id.let {
            when (it) {
                R.id.text_view_value_location_city_activity_home -> {
                    startActivityForResult(Intent(this@HomeActivity, CityActivity::class.java), REQUEST_CODE_CITY)
                }
                R.id.button_submit_activity_home -> {
                    if (text_view_value_location_city_activity_home.text.equals(getString(R.string.choose_location))) {
                        Snackbar.make(
                                findViewById(android.R.id.content),
                                getString(R.string.please_choose_location),
                                Snackbar.LENGTH_SHORT
                        ).show()
                    } else {
                        doLoadDataPrayerSchedule()
                    }
                }
                else -> {
                    /** nothing to do in here */
                }
            }
        }
    }

    /**
     * @description Load data prayer schedule
     */
    private fun doLoadDataPrayerSchedule() {
        val progressDialog = ProgressDialog(this)
        progressDialog.let {
            it.setCancelable(false)
            it.setMessage(getString(R.string.please_wait))
            it.show()
        }
        launch(Android) {
            val resultDataPrayerSchedule = ShalatClient
                    .getPrayerScheduleData(id = dataCity!!.id.toInt())
                    .await()
            progressDialog.let {
                if (it.isShowing) {
                    it.dismiss()
                }
            }
            val intentPrayerScheduleActivity = Intent(this@HomeActivity, PrayerScheduleActivity::class.java)
            intentPrayerScheduleActivity.putExtra("dataPrayer", resultDataPrayerSchedule)
            intentPrayerScheduleActivity.putExtra("locationCity", dataCity!!.namaKota)
            startActivity(intentPrayerScheduleActivity)
        }
    }

    /**
     * @description Activity result from another activity
     * @param requestCode {Int} request code when start other activity
     * @param resultCode {Int} result code when back to this activity
     * @param data {Intent} Intent data from another activity
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    REQUEST_CODE_CITY -> {
                        val bundle = data?.extras
                        val dataCity = bundle?.get("data") as Data
                        this@HomeActivity.dataCity = dataCity.copy()
                        text_view_value_location_city_activity_home.text = dataCity.namaKota
                    }
                    else -> {
                        /** nothing to do in here */
                    }
                }
            }
            else -> {
                /** nothing to do in here */
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
