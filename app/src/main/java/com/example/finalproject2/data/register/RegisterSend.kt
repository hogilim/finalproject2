package com.example.finalproject2.data.register

import java.io.Serializable

data class RegisterSend(
    val user_id : String,
    val user_password : String,
    val nickname : String,
    val location : UserLocation
)


data class UserLocation(
    val si : String,
    val gu : String,
    //val dong : String
) : Serializable
