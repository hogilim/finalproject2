package com.example.finalproject2.data

data class Data(
    val response : Response
)

data class Response(
    val body : Body,
    val header : Header
)

data class Body(
    val totalCount : String,
    val items : List<Items>,
    val pageNo : Int,
    val numOfRows : Int
)

data class Items(
    val clearVal : String,
    val sn : String,
    val districtName : String,
    val dataDate : String,
    val issueVal : String,
    val issueTime : String,
    val clearDate : String,
    val issueDate : String,
    val moveName : String,
    val clearTime : String,
    val issueGbn : String,
    val itemCode : String
)

class Header(
    val resultMsg : String,
    val resultCode : String
)