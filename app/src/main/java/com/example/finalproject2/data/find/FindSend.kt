package com.example.finalproject2.data.find

import com.example.finalproject2.data.register.UserLocation

data class FindSend(
    val sid : Long,
    val location : UserLocation,
    val title : String,
    val body : String,
    val time : String,
    val image : String // 전송타입?
)
