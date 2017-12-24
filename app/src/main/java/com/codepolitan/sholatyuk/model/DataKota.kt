package com.codepolitan.sholatyuk.model

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
)
