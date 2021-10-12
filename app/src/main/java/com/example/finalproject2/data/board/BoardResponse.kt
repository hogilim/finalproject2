package com.example.finalproject2.data.board

data class BoardResponse(
    val lastpageno : Int,
    val items : List<Item>,
)


data class Item(
    val sid : Long,
    val nickname : String,
    val image : String,  // image 어떤형식으로 보내줄건지?
    val dogname : String,
    val title : String
)