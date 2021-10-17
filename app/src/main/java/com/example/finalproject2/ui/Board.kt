package com.example.finalproject2.ui

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject2.R
import com.example.finalproject2.data.register.UserLocation
import com.example.finalproject2.databinding.ActivityBoardBinding
import com.example.finalproject2.retrofit2.RetrofitClient
import com.example.finalproject2.retrofit2.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime

class Board:AppCompatActivity() {
    val dataSet = ArrayList<BoardUnit>()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecycler(binding)
        swipeRefresh(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initRecycler(binding: ActivityBoardBinding) {

        val response = BoardUnit(1,
            1,
            arrayListOf<Long>(1),
            arrayListOf<String>("https://ww.namu.la/s/0826fcb62ab5ffd031695083aa629d99351834b91417a1c9fee4a2a1a4b64bd8287e88163820b02176526fe7006fb51438fbb6f42cb2438497e298e722eac77cde9da7d51d8fa4d36800670013fb43b70d35328129d1f9aec0f9a5ee05ae7fe4","https://newsimg.hankookilbo.com/cms/articlerelease/2019/04/29/201904291390027161_3.jpg"),
            "hogi",
            "dungdung",
            "고양이 보고가세요",
            "고양이 보고가세요~",
            "male",
            UserLocation("서울","관악"),
            LocalDateTime.now(),
            LocalDateTime.now()
        )

        val uri = arrayListOf<String>("https://ww.namu.la/s/0826fcb62ab5ffd031695083aa629d99351834b91417a1c9fee4a2a1a4b64bd8287e88163820b02176526fe7006fb51438fbb6f42cb2438497e298e722eac77cde9da7d51d8fa4d36800670013fb43b70d35328129d1f9aec0f9a5ee05ae7fe4","https://newsimg.hankookilbo.com/cms/articlerelease/2019/04/29/201904291390027161_3.jpg")


        dataSet.apply {
            add(response)

        }
        val myAdapter = BoardAdapter(this, dataSet)
        binding.rv.adapter = myAdapter
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun swipeRefresh(binding: ActivityBoardBinding){
        binding.srl.setOnRefreshListener {
            dataSet.add(BoardUnit(1,
                1,
                arrayListOf<Long>(1),
                arrayListOf<String>("https://ww.namu.la/s/0826fcb62ab5ffd031695083aa629d99351834b91417a1c9fee4a2a1a4b64bd8287e88163820b02176526fe7006fb51438fbb6f42cb2438497e298e722eac77cde9da7d51d8fa4d36800670013fb43b70d35328129d1f9aec0f9a5ee05ae7fe4","https://newsimg.hankookilbo.com/cms/articlerelease/2019/04/29/201904291390027161_3.jpg"),
                "hogi",
                "dungdung",
                "고양이 보고가세요",
                "고양이 보고가세요~",
                "male",
                UserLocation("서울","관악"),
                LocalDateTime.now(),
                LocalDateTime.now()
            ))

            binding.srl.isRefreshing = false
        }

    }
}