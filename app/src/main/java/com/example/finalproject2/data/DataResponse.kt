package com.example.finalproject2.data

import com.google.gson.annotations.SerializedName

data class DataResponse(
    @SerializedName("items")
    val items : List<Datas>
)

data class Datas(
    @SerializedName("districtName")
    val districtName : String,
    @SerializedName("sn")
    val sn : Int
)
