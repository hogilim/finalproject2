package com.example.finalproject2.data.register

import java.io.Serializable

data class RegisterSend(
    val userId : String,
    val password : String,
    val nickName : String,
    val location : UserLocation,
    val userToken : String
)


data class UserLocation(
    val si : String,
    val gu : String,
    //val dong : String
) : Serializable
