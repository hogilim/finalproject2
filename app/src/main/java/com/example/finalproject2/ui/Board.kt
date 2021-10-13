package com.example.finalproject2.ui

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject2.R
import com.example.finalproject2.data.register.UserLocation
import com.example.finalproject2.databinding.ActivityBoardBinding

class Board:AppCompatActivity() {
    val dataSet = ArrayList<BoardUnit>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecycler(binding)

    }

    private fun initRecycler(binding: ActivityBoardBinding){
        val bd = getDrawable(R.drawable.d1) as BitmapDrawable
        val bitmap = bd.bitmap
        dataSet.apply {
            add(BoardUnit(imgProfile = bitmap, name = "mary", gender = "male", location = UserLocation("난향","관악"), "1"))
            add(BoardUnit(imgProfile = bitmap, name = "mary", gender = "male", location = UserLocation("난향","관악"), "1"))
            add(BoardUnit(imgProfile = bitmap, name = "mary", gender = "male", location = UserLocation("난향","관악"), "1"))
            add(BoardUnit(imgProfile = bitmap, name = "mary", gender = "male", location = UserLocation("난향","관악"), "1"))
            add(BoardUnit(imgProfile = bitmap, name = "mary", gender = "male", location = UserLocation("난향","관악"), "1"))
            add(BoardUnit(imgProfile = bitmap, name = "mary", gender = "male", location = UserLocation("난향","관악"), "1"))
            add(BoardUnit(imgProfile = bitmap, name = "mary", gender = "male", location = UserLocation("난향","관악"), "1"))
            add(BoardUnit(imgProfile = bitmap, name = "mary", gender = "male", location = UserLocation("난향","관악"), "1"))
            add(BoardUnit(imgProfile = bitmap, name = "mary", gender = "male", location = UserLocation("난향","관악"), "1"))
            add(BoardUnit(imgProfile = bitmap, name = "mary", gender = "male", location = UserLocation("난향","관악"), "1"))
            add(BoardUnit(imgProfile = bitmap, name = "mary", gender = "male", location = UserLocation("난향","관악"), "1"))
            add(BoardUnit(imgProfile = bitmap, name = "mary", gender = "male", location = UserLocation("난향","관악"), "1"))
        }
        val myAdapter = BoardAdapter(dataSet)
        binding.rv.adapter = myAdapter
    }
}