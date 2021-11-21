package com.example.finalproject2.data.lost

import com.example.finalproject2.data.register.UserLocation

data class LostSend(
    val memberId : Long,
    val location : UserLocation,
    val title : String,
    val dogName : String,
    val dogGender : String,
    val content : Int,
    val gender : String,
    val files : ArrayList<String>
)
