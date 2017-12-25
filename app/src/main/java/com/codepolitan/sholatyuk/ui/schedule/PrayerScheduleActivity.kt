package com.codepolitan.sholatyuk.ui.schedule

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.codepolitan.sholatyuk.R
import com.codepolitan.sholatyuk.model.DataJadwalSholat
import com.codepolitan.sholatyuk.ui.schedule.adapter.AdapterPrayerSchedule
import kotlinx.android.synthetic.main.activity_prayer_schedule.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PrayerScheduleActivity : AppCompatActivity() {

    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prayer_schedule)
        initToolbar()
        doLoadData()
    }

    private fun doLoadData() {
        val bundle = intent.extras
        val dataPrayer = bundle.get("dataPrayer") as DataJadwalSholat
        val locationCity = bundle.getString("locationCity")
        text_view_location_city_activity_prayer_schedule.text = locationCity
        text_view_date_activity_prayer_schedule.text = SimpleDateFormat("HH:mm EEE, MMMM d, yyyy", Locale.US).format(Date())

        val listPrayTime = ArrayList<String>()
        val listPrayName = ArrayList<String>()
        val dateNow = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date()).let {
            it.substring(it.length - 2, it.length)
        }
        for (prayerItem in dataPrayer.data) {
            if (prayerItem.tanggal == dateNow) {
                listPrayTime.let {
                    it.add(prayerItem.shubuh)
                    it.add(prayerItem.dzuhur)
                    it.add(prayerItem.ashr)
                    it.add(prayerItem.maghrib)
                    it.add(prayerItem.isya)
                }
                listPrayName.let {
                    it.add("shubuh")
                    it.add("dzuhur")
                    it.add("ashr")
                    it.add("maghrib")
                    it.add("isya")
                }
                break
            }
        }

        recycler_view_data_prayer_schedule_activity_prayer_schedule.let {
            val adapterPrayerSchedule = AdapterPrayerSchedule(
                    this,
                    listPrayTime,
                    listPrayName
            )
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapterPrayerSchedule
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar_activity_prayer_schedule)
        supportActionBar?.let {
            it.title = ""
            it.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
            item?.itemId.let {
                return when (it) {
                    android.R.id.home -> {
                        onBackPressed()
                        true
                    }
                    else -> {
                        super.onOptionsItemSelected(item)
                    }
                }
            }
}
