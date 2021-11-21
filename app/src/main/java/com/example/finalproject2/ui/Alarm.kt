package com.example.finalproject2.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject2.data.CommonResponse
import com.example.finalproject2.data.board.Page
import com.example.finalproject2.databinding.ActivityAlarmBinding
import com.example.finalproject2.databinding.ActivityShowitemBinding
import com.example.finalproject2.retrofit2.RetrofitClient
import com.example.finalproject2.retrofit2.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Alarm :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAlarmBinding.inflate(layoutInflater)
        val retrofit = RetrofitClient.getInstnace() //
        val myAPI = retrofit.create(RetrofitService::class.java)
        setContentView(binding.root)

        myAPI.alarm(
        ).enqueue(object : Callback<CommonResponse> {

            //이때 onFaliure는 Call을 서버쪽으로 아예 보내지 못한 경우입니다.
            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                Toast.makeText(this@Alarm, "오류 다시 시도하세요.", Toast.LENGTH_SHORT).show()
                println(t.message)
            }


            //만약 보낸 것이 성공했을 경우는 resonse를 가지고 들어옵니다.
            //그리고 call을 때릴 때 RawResponseData로 갔으니까 Reponse도 그 타입을 가지고 옵니다.
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {
                println("response : ${response.errorBody()}")
                println("response : ${response.message()}")
                println("response : ${response.code()}")
                println("response : ${response.raw().request.url.toUrl()}")
                println("response : ${response.body()!!}")

                val datatmp = response.body()!!
                val data = datatmp.data
                for (i in data.fileUris)
                    println(i)
                val Images = data.fileUris

                val madapter = ShowItemImageAdapter(Images)
                binding.dogimagelist.adapter = madapter

                binding.name.text = "지역 :   " + data.address?.gu
                binding.gender.text = "성별 :   " + data.gender
                binding.content.text = data.content
                binding.date.text = data.regDate.substring(0,10)

            }
        })

    }
}