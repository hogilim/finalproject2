package com.example.finalproject2.ui

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
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

    private fun initRecycler(binding: ActivityBoardBinding) {
        val bitmap = arrayListOf<String>("https://ww.namu.la/s/0826fcb62ab5ffd031695083aa629d99351834b91417a1c9fee4a2a1a4b64bd8287e88163820b02176526fe7006fb51438fbb6f42cb2438497e298e722eac77cde9da7d51d8fa4d36800670013fb43b70d35328129d1f9aec0f9a5ee05ae7fe4","https://newsimg.hankookilbo.com/cms/articlerelease/2019/04/29/201904291390027161_3.jpg")
        dataSet.apply {
            add(
                BoardUnit(
                    imgProfile = bitmap,
                    name = "1",
                    gender = "male",
                    location = UserLocation("난향", "관악"),
                    "1"
                )
            )
            add(
                BoardUnit(
                    imgProfile = bitmap,
                    name = "2",
                    gender = "male",
                    location = UserLocation("난향", "관악"),
                    "1"
                )
            )
            add(
                BoardUnit(
                    imgProfile = bitmap,
                    name = "3",
                    gender = "male",
                    location = UserLocation("난향", "관악"),
                    "1"
                )
            )
            add(
                BoardUnit(
                    imgProfile = bitmap,
                    name = "4",
                    gender = "male",
                    location = UserLocation("난향", "관악"),
                    "1"
                )
            )
            add(
                BoardUnit(
                    imgProfile = bitmap,
                    name = "5",
                    gender = "male",
                    location = UserLocation("난향", "관악"),
                    "1"
                )
            )
            add(
                BoardUnit(
                    imgProfile = bitmap,
                    name = "6",
                    gender = "male",
                    location = UserLocation("난향", "관악"),
                    "1"
                )
            )
            add(
                BoardUnit(
                    imgProfile = bitmap,
                    name = "7",
                    gender = "male",
                    location = UserLocation("난향", "관악"),
                    "1"
                )
            )
            add(
                BoardUnit(
                    imgProfile = bitmap,
                    name = "8",
                    gender = "male",
                    location = UserLocation("난향", "관악"),
                    "1"
                )
            )
            add(
                BoardUnit(
                    imgProfile = bitmap,
                    name = "9",
                    gender = "male",
                    location = UserLocation("난향", "관악"),
                    "1"
                )
            )
            add(
                BoardUnit(
                    imgProfile = bitmap,
                    name = "10",
                    gender = "male",
                    location = UserLocation("난향", "관악"),
                    "1"
                )
            )
        }
        val myAdapter = BoardAdapter(this, dataSet)
        binding.rv.adapter = myAdapter
    }
}