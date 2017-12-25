package com.codepolitan.sholatyuk.ui.city

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.codepolitan.sholatyuk.R
import com.codepolitan.sholatyuk.db.DatabaseHelper
import com.codepolitan.sholatyuk.model.Data
import com.codepolitan.sholatyuk.ui.city.adapter.AdapterDataCity
import kotlinx.android.synthetic.main.activity_city.*

class CityActivity : AppCompatActivity() {

    private val TAG = javaClass.simpleName
    private val databaseHelper by lazy {
        DatabaseHelper(
                context = this@CityActivity,
                name = DatabaseHelper.DATABASE_NAME,
                factory = null,
                version = DatabaseHelper.DATABASE_VERSION
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
        initToolbar()
        doLoadData()
    }

    private fun doLoadData() {
        val listDataCity = databaseHelper.getDataCity()
        recycler_view_data_city_activity_city.let {
            val adapterDataCity = AdapterDataCity(
                    listDataCity = listDataCity,
                    listenerAdapterDataCity = object : AdapterDataCity.ListenerAdapterDataCity {
                        override fun clickItem(data: Data) {
                            val intent = Intent()
                            intent.putExtra("data", data)
                            setResult(Activity.RESULT_OK, intent)
                            finish()
                        }
                    }
            )
            val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
            it.addItemDecoration(dividerItemDecoration)
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapterDataCity
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar_activity_city)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = getString(R.string.choose_location)
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
