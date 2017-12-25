package com.codepolitan.sholatyuk.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


/**
 * Created by yudisetiawan on 12/23/17.
 */

data class DataKota(
        @SerializedName("status") val status: String,
		@SerializedName("msg") val msg: String,
		@SerializedName("count") val count: Int,
		@SerializedName("data") val data: List<Data>
)

data class Data(
		@SerializedName("id") val id: String,
		@SerializedName("nama_kota") val namaKota: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(namaKota)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Data> {
        override fun createFromParcel(parcel: Parcel): Data {
            return Data(parcel)
        }

        override fun newArray(size: Int): Array<Data?> {
            return arrayOfNulls(size)
        }
    }
}
