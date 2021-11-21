package com.example.finalproject2.ui

import android.R
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject2.data.register.RegisterResponse
import com.example.finalproject2.data.register.RegisterSend
import com.example.finalproject2.data.register.UserLocation
import com.example.finalproject2.databinding.ActivityRegisterBinding
import com.example.finalproject2.retrofit2.RetrofitClient
import com.example.finalproject2.retrofit2.RetrofitService
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.NullPointerException

class Register:AppCompatActivity() {
    lateinit var token : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val retrofit = RetrofitClient.getInstnace() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        val myAPI = retrofit.create(RetrofitService::class.java)

        val location_Si = resources.getStringArray(com.example.finalproject2.R.array.si)
        val location_gu = resources.getStringArray(com.example.finalproject2.R.array.gu)
        val location_Dong = resources.getStringArray(com.example.finalproject2.R.array.dong)
        val adapter_Si = ArrayAdapter<String>(this, R.layout.simple_list_item_1, location_Si)
        val adapter_gu = ArrayAdapter<String>(this, R.layout.simple_list_item_1, location_gu)
        val adapter_Dong =
            ArrayAdapter<String>(this, R.layout.simple_list_item_1, location_Dong)

        binding.selectSi.adapter = adapter_Si
        binding.selectGu.adapter = adapter_gu
        binding.selectDong.adapter = adapter_Dong
        println("!!!!!!!!!!!!!")
        getToken(binding)
        register(binding,myAPI,location_Si,location_gu,location_Dong)

    }

    fun getToken(binding: ActivityRegisterBinding){
        Runnable{
            try {
                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.i(TAG, "getInstanceId failed", task.exception)
                        return@addOnCompleteListener
                    }
                    token = task.result!!
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.run()
    }


    fun register(binding: ActivityRegisterBinding, myAPI: RetrofitService,
    location_Si:Array<String>, location_gu:Array<String>, location_Dong:Array<String>){
        binding.btnRegister.setOnClickListener {
            var selected_si = ""
            var selected_gu = ""
            var selected_dong = ""
            binding.selectSi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selected_si = location_Si[position]
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    selected_si = "no"
                }
            }
            binding.selectGu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selected_gu = location_gu[position]
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
            binding.selectDong.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selected_dong = location_Dong[position]
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
            var isExistBlank = false
            var isPWSame = false

            val id = binding.editId.text.toString()
            val nickname = binding.editNickname.text.toString()
            val pw = binding.editPw.text.toString()
            val pw_re = binding.editPwRe.text.toString()
            val si = selected_si
            val gu = selected_gu
            val dong = selected_dong

            // 안채운 양식 확인
            if(id.isEmpty()||nickname.isEmpty()||pw.isEmpty()||pw_re.isEmpty()||si=="시"||gu=="구"){
                Toast.makeText(this, "모든 정보를 작성해주세요.", Toast.LENGTH_SHORT).show()
                isExistBlank = true
            }
            // 패스워드 확인
            if(pw == pw_re){
                isPWSame = true
            }
            else{
                Toast.makeText(this, "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show()
                isPWSame = false
            }

            // 양식 다 채우고 패스워드 잘 입력 시 서버 통신
            if(!isExistBlank && isPWSame){
                // 서버 데이터 전송 후 유효하면 가입 성공 유효하지 않으면 실패 메시지
                var registerCheck : Long = -1
                Runnable {
                    myAPI.register(
                        RegisterSend(id,pw,nickname, UserLocation("서울시", gu), token)
                    ).enqueue(object : Callback<RegisterResponse> {

                        //이때 onFaliure는 Call을 서버쪽으로 아예 보내지 못한 경우입니다.
                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            Toast.makeText(this@Register,"오류 다시 시도하세요.", Toast.LENGTH_SHORT).show()
                            println(t.message)
                        }


                        //만약 보낸 것이 성공했을 경우는 resonse를 가지고 들어옵니다.
                        //그리고 call을 때릴 때 RawResponseData로 갔으니까 Reponse도 그 타입을 가지고 옵니다.
                        override fun onResponse(
                            call: Call<RegisterResponse>,
                            response: Response<RegisterResponse>
                        ) {
                            println("response : ${response.errorBody()}")
                            println("response : ${response.message()}")
                            println("response : ${response.code()}")
                            println("response : ${response.raw().request.url.toUrl()}")
                            println("response : ${response.body()!!}")
                            try {
                                registerCheck = response.body()!!.data
                                if (registerCheck >= 0) {
                                    Toast.makeText(
                                        this@Register,
                                        "회원가입 성공! 로그인하세요.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    finish()
                                } else {
                                    Toast.makeText(this@Register, "회원가입 실패.", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }catch (e:NullPointerException){
                                Toast.makeText(this@Register,"오류 다시 시도하세요.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                }.run()
                /*
                Runnable {
                    myAPI.test().enqueue(object : Callback<Boolean> {

                        //이때 onFaliure는 Call을 서버쪽으로 아예 보내지 못한 경우입니다.
                        override fun onFailure(call: Call<Boolean>, t: Throwable) {
                            Toast.makeText(this@Register,"오류 다시 시도하세요.", Toast.LENGTH_SHORT).show()
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
                            registerCheck = response.body()!!
                            if(registerCheck) {
                                Toast.makeText(this@Register, "회원가입 성공! 로그인하세요.", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            else{
                                Toast.makeText(this@Register, "회원가입 실패.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                }.run()*/
            }

        }
    }
}