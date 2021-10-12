package com.example.finalproject2.data.lost

import com.example.finalproject2.data.register.UserLocation

data class LostSend(
    val sid : Long,
    val location : UserLocation,
    val title : String,
    val dogname : String,
    val doggender : String,
    val dogage : Int,
    val body : String,
    val time : String,
    val image : String
)
