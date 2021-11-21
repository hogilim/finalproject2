package com.example.finalproject2.data

import java.time.LocalDateTime

data class ErrorResponse(
    val localDateTime : LocalDateTime,
    val status : Int,
    val error : String,
    val code : String,
    val message : String
)
