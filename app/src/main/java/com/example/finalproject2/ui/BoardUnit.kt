package com.example.finalproject2.ui

import android.graphics.Bitmap
import android.net.Uri
import com.example.finalproject2.data.register.UserLocation
import java.io.Serializable

data class BoardUnit (
    val imgProfile: ArrayList<String>,
    val name: String,
    val gender: String,
    val location: UserLocation,
    val date: String
) : Serializable
