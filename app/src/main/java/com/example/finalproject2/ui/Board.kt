package com.example.finalproject2.ui

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject2.R
import com.example.finalproject2.data.board.BoardResponse
import com.example.finalproject2.data.board.BoardSend
import com.example.finalproject2.data.board.Pageable
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
    val myAdapter = BoardAdapter(this, dataSet)
    var totpage: Int = 0
    var memberId :Long = 0
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // retrofit instance
        val retrofit = RetrofitClient.getInstnace() //
        val myAPI = retrofit.create(RetrofitService::class.java)
        binding.rv.adapter = myAdapter

        initRecycler(binding,myAPI)
        swipeRefresh(binding,myAPI)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initRecycler(binding: ActivityBoardBinding, myAPI : RetrofitService) {  // 처음 진입시 10개 로
        var res : BoardResponse
        /*
        myAPI.board(
            BoardSend(10, 0)
        ).enqueue(object : Callback<BoardResponse> {

            //이때 onFaliure는 Call을 서버쪽으로 아예 보내지 못한 경우입니다.
            override fun onFailure(call: Call<BoardResponse>, t: Throwable) {
                Toast.makeText(this@Board,"오류 다시 시도하세요.", Toast.LENGTH_SHORT).show()
                println(t.message)
            }


            //만약 보낸 것이 성공했을 경우는 resonse를 가지고 들어옵니다.
            //그리고 call을 때릴 때 RawResponseData로 갔으니까 Reponse도 그 타입을 가지고 옵니다.
            override fun onResponse(
                call: Call<BoardResponse>,
                response: Response<BoardResponse>
            ) {
                println("response : ${response.errorBody()}")
                println("response : ${response.message()}")
                println("response : ${response.code()}")
                println("response : ${response.raw().request.url.toUrl()}")
                println("response : ${response.body()!!}")
                res = response.body()!!
            }
        })
         */
        val a = arrayListOf(BoardUnit(1,
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
        val p = Pageable(10,0,0,true,false)
        res = BoardResponse(a,p,10,10,false)
        totpage = 1
        dataSet.addAll(res.content)
        myAdapter.dataUpdate()

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun swipeRefresh(binding: ActivityBoardBinding,myAPI: RetrofitService){
        binding.srl.setOnRefreshListener {
            initRecycler(binding, myAPI)
            binding.srl.isRefreshing = false
        }
    }
    private fun scrollUpdate(binding: ActivityBoardBinding, myAPI: RetrofitService){

    }
}