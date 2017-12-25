package com.codepolitan.sholatyuk.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.codepolitan.sholatyuk.model.Data
import java.sql.SQLException

/**
 * Created by yudisetiawan on 12/23/17.
 */
class DatabaseHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    companion object {
        val DATABASE_NAME = "shalatyuk.db"
        val DATABASE_VERSION = 1
    }
    private val TAG = javaClass.simpleName

    private val CITY_TABLE_NAME = "kota"
    private val CITY_TABLE_NAME_COLUMN_ID = "id"
    private val CITY_TABLE_NAME_COLUMN_CITY_NAME = "name"
    private val CITY_TABLE_SELECT = "select * from $CITY_TABLE_NAME"
    private val CITY_TABLE_CREATE = "create table if not exists $CITY_TABLE_NAME " +
            "($CITY_TABLE_NAME_COLUMN_ID integer primary key," +
            "$CITY_TABLE_NAME_COLUMN_CITY_NAME text " +
            ")"
    private val CITY_TABLE_DROP = "drop table if exists $CITY_TABLE_NAME"

    override fun onCreate(sqliteDatabase: SQLiteDatabase?) {
        try {
            sqliteDatabase?.execSQL(CITY_TABLE_CREATE)
        } catch (sqle: SQLException) {
            sqle.printStackTrace()
        }
    }

    override fun onUpgrade(sqliteDatabase: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        try {
            sqliteDatabase?.execSQL(CITY_TABLE_DROP)
            sqliteDatabase?.execSQL(CITY_TABLE_CREATE)
        } catch (sqle: SQLException) {
            sqle.printStackTrace()
        }
    }

    /**
     * Insert data city to city_table
     */
    fun insertDataCity(data: Data): Long {
        try {
            val sqliteDatabase = writableDatabase
            val contentValue = ContentValues()
            contentValue.put(CITY_TABLE_NAME_COLUMN_ID, data.id)
            contentValue.put(CITY_TABLE_NAME_COLUMN_CITY_NAME, data.namaKota)
            return sqliteDatabase.insert(
                    CITY_TABLE_NAME,
                    null,
                    contentValue
            )
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Insert list data city to city_table
     */
    fun insertDataCity(listData: List<Data>): Int {
        try {
            val sqliteDatabase = writableDatabase
            val queryInsert = "insert into $CITY_TABLE_NAME " +
                    "($CITY_TABLE_NAME_COLUMN_ID, $CITY_TABLE_NAME_COLUMN_CITY_NAME) " +
                    "values " +
                    "(?, ?)"
            sqliteDatabase.beginTransaction()
            val sqliteStatement = sqliteDatabase.compileStatement(queryInsert)
            for (data in listData) {
                sqliteStatement.bindString(1, data.id)
                sqliteStatement.bindString(2, data.namaKota)
                sqliteStatement.execute()
                sqliteStatement.clearBindings()
            }
            sqliteDatabase.setTransactionSuccessful()
            sqliteDatabase.endTransaction()
            return 1
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Delete data city in city_table by id
     */
    fun deleteDataCityById(id: Int): Int {
        try {
            val sqliteDatabase = writableDatabase
            return sqliteDatabase.delete(
                    CITY_TABLE_NAME,
                    "$CITY_TABLE_NAME_COLUMN_ID = ?",
                    Array(1, { id.toString() })
            )
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Delete all data city in city_table
     */
    fun deleteDataCity(): Int {
        try {
            val sqliteDatabase = writableDatabase
            return sqliteDatabase.delete(
                    CITY_TABLE_NAME,
                    null,
                    null
            )
        } catch (e: Exception) {
            throw e
        }
    }

    fun countDataCity(): Int {
        var itemCount: Int
        try {
            val sqliteDatabase = writableDatabase
            val cursor = sqliteDatabase.rawQuery(
                    CITY_TABLE_SELECT,
                    null,
                    null
            )
            itemCount = cursor.count
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
        return itemCount
    }

    fun getDataCity(): List<Data> {
        val listDataCity = ArrayList<Data>()
        try {
            val sqliteDatabase = writableDatabase
            val cursor = sqliteDatabase.rawQuery(
                    CITY_TABLE_SELECT,
                    null,
                    null
            )
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val dataKota = Data(
                            id = cursor.getInt(cursor.getColumnIndex(CITY_TABLE_NAME_COLUMN_ID)).toString(),
                            namaKota = cursor.getString(cursor.getColumnIndex(CITY_TABLE_NAME_COLUMN_CITY_NAME))
                    )
                    listDataCity.add(dataKota)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return listDataCity
    }

}