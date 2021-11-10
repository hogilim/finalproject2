package com.example.finalproject2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.NonNull
import com.example.finalproject2.R
import com.example.finalproject2.alarm.MyFirebaseMessagingService
import com.example.finalproject2.data.login.LoginResponse
import com.example.finalproject2.data.login.LoginSend
import com.example.finalproject2.databinding.ActivityMainBinding
import com.example.finalproject2.retrofit2.RetrofitClient
import com.example.finalproject2.retrofit2.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.security.AccessController.getContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // retrofit instance
        val retrofit = RetrofitClient.getInstnace() //
        val myAPI = retrofit.create(RetrofitService::class.java)
        login(binding, myAPI)
        register(binding)
    }

    private fun login(binding: ActivityMainBinding, myAPI: RetrofitService) {
        binding.btnLogin.setOnClickListener {
            val id = binding.editId.text.toString()
            val pw = binding.editPw.text.toString()
            // 입력 확인
            if (id.isEmpty() || pw.isEmpty()) {
                Toast.makeText(this, "ID와 PW를 모두 입력하세요", Toast.LENGTH_SHORT).show()
            } else {
                val loginSend = LoginSend(id, pw)
                /*
                Runnable {

                    myAPI.login(
                       loginSend
                    ).enqueue(object : Callback<LoginResponse> {

                        //이때 onFaliure는 Call을 서버쪽으로 아예 보내지 못한 경우입니다.
                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(this@MainActivity,"오류 다시 시도하세요.", Toast.LENGTH_SHORT).show()
                            println(t.message)
                        }


                        //만약 보낸 것이 성공했을 경우는 resonse를 가지고 들어옵니다.
                        //그리고 call을 때릴 때 RawResponseData로 갔으니까 Reponse도 그 타입을 가지고 옵니다.
                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            Toast.makeText(this@MainActivity,"로그인 성공!", Toast.LENGTH_SHORT).show()
                            println("response : ${response.errorBody()}")
                            println("response : ${response.message()}")
                            println("response : ${response.code()}")
                            println("response : ${response.raw().request.url.toUrl()}")
                            println("response : ${response.body()!!}")
                            val intent = Intent(this@MainActivity,Board::class.java)
                            startActivity(intent)
                        }
                    })
                }.run()
                     */
                Runnable {
                    myAPI.test().enqueue(object : Callback<Boolean> {

                        //이때 onFaliure는 Call을 서버쪽으로 아예 보내지 못한 경우입니다.
                        override fun onFailure(call: Call<Boolean>, t: Throwable) {
                            Toast.makeText(this@MainActivity,"오류 다시 시도하세요.", Toast.LENGTH_SHORT).show()
                            println(t.message)
                        }


                        //만약 보낸 것이 성공했을 경우는 resonse를 가지고 들어옵니다.
                        //그리고 call을 때릴 때 RawResponseData로 갔으니까 Reponse도 그 타입을 가지고 옵니다.
                        override fun onResponse(
                            call: Call<Boolean>,
                            response: Response<Boolean>
                        ) {
                            println("response : ${response.errorBody()}")
                            println("response : ${response.message()}")
                            println("response : ${response.code()}")
                            println("response : ${response.raw().request.url.toUrl()}")
                            println("response : ${response.body()!!}")
                            if(response.body()!!) {
                                Toast.makeText(this@MainActivity, "로그인 성공!", Toast.LENGTH_SHORT)
                                    .show()
                                val intent = Intent(this@MainActivity, Board::class.java)
                                startActivity(intent)
                            }
                        }
                    })
                }.run()
            }
        }
    }

    fun register(binding: ActivityMainBinding) {
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }

}


