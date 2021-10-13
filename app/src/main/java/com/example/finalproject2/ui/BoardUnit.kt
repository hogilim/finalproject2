package com.example.finalproject2.ui

import android.graphics.Bitmap
import com.example.finalproject2.data.register.UserLocation

data class BoardUnit (
    val imgProfile: Bitmap,
    val name: String,
    val gender: String,
    val location: UserLocation,
    val date: String
)