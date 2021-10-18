package com.example.finalproject2.data.board

import com.example.finalproject2.ui.BoardUnit

data class BoardResponse(
    val content : ArrayList<BoardUnit>,
    val pageable : Pageable,
    val totalPages : Int,
    val totalElements : Int,
    val last : Boolean
)

data class Pageable(
    val pageSize: Int,
    val pageNumber: Int,
    val offset: Int,
    val paged: Boolean,
    val empty: Boolean
)