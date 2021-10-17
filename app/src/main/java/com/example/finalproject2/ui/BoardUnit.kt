package com.example.finalproject2.ui

import com.example.finalproject2.data.register.UserLocation
import java.io.Serializable
import java.time.LocalDateTime

data class BoardUnit (
    val id: Long,
    val memberId: Long,
    val fileIds: ArrayList<Long>,
    val fileUris: ArrayList<String>,
    val nickName: String,
    val dogName: String,
    val title: String,
    val content: String,
    val gender: String,
    val address: UserLocation,
    val regDate : LocalDateTime,
    val lastModifiedDate: LocalDateTime
) : Serializable
