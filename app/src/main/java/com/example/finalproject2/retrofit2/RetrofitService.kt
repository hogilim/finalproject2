package com.example.finalproject2.retrofit2

import com.example.finalproject2.data.board.BoardResponse
import com.example.finalproject2.data.board.BoardSend
import com.example.finalproject2.data.login.LoginResponse
import com.example.finalproject2.data.login.LoginSend
import com.example.finalproject2.data.lost.LostSend
import com.example.finalproject2.data.register.RegisterResponse
import com.example.finalproject2.data.register.RegisterSend
import com.example.finalproject2.data.unit.UnitResponse
import com.example.finalproject2.data.unit.UnitSend
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Body

interface RetrofitService {
    @GET("hogi")
    fun test():Call<Boolean>

    @Headers("content-type: application/json")
    @POST("register")
    fun register(
        @Body register : RegisterSend
    ):Call<RegisterResponse>

    @Headers("content-type: application/json")
    @POST("login")
    fun login(
        @Body login : LoginSend
    ):Call<LoginResponse>

    @Headers("content-type: application/json")
    @POST("Board")
    fun board(
        @Body board : BoardSend
    ):Call<BoardResponse>

    @Headers("content-type: application/json")
    @POST("Unit")
    fun unit(
        @Body unit : UnitSend
    ):Call<UnitResponse>

    @Multipart
    @POST("test-file-up1")
    fun find(
        @Part imageList : ArrayList<MultipartBody.Part>,
        @PartMap map : MutableMap<String, RequestBody>
    ):Call<String>

    @Headers("content-type: application/json")
    @POST("lost")
    fun lost(
        @Body lost : LostSend
    ):Call<String>
}