package com.codepolitan.sholatyuk.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


/**
 * Created by yudisetiawan on 12/24/17.
 */

data class DataJadwalSholat(
        @SerializedName("status") val status: String,
        @SerializedName("msg") val msg: String,
        @SerializedName("count") val count: Int,
        @SerializedName("data") val data: List<DataSholat>
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.createTypedArrayList(DataSholat))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(status)
        parcel.writeString(msg)
        parcel.writeInt(count)
        parcel.writeTypedList(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataJadwalSholat> {
        override fun createFromParcel(parcel: Parcel): DataJadwalSholat {
            return DataJadwalSholat(parcel)
        }

        override fun newArray(size: Int): Array<DataJadwalSholat?> {
            return arrayOfNulls(size)
        }
    }
}

data class DataSholat(
        @SerializedName("tanggal") val tanggal: String,
        @SerializedName("shubuh") val shubuh: String,
        @SerializedName("dzuhur") val dzuhur: String,
        @SerializedName("ashr") val ashr: String,
        @SerializedName("maghrib") val maghrib: String,
        @SerializedName("isya") val isya: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(tanggal)
        parcel.writeString(shubuh)
        parcel.writeString(dzuhur)
        parcel.writeString(ashr)
        parcel.writeString(maghrib)
        parcel.writeString(isya)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataSholat> {
        override fun createFromParcel(parcel: Parcel): DataSholat {
            return DataSholat(parcel)
        }

        override fun newArray(size: Int): Array<DataSholat?> {
            return arrayOfNulls(size)
        }
    }
}