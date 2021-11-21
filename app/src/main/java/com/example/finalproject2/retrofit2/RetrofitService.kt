package com.example.finalproject2.retrofit2

import com.example.finalproject2.data.CommonResponse
import com.example.finalproject2.data.board.Page
import com.example.finalproject2.data.board.BoardSend
import com.example.finalproject2.data.login.LoginResponse
import com.example.finalproject2.data.login.LoginSend
import com.example.finalproject2.data.lost.LostSend
import com.example.finalproject2.data.register.RegisterResponse
import com.example.finalproject2.data.register.RegisterSend
import com.example.finalproject2.data.unit.UnitResponse
import com.example.finalproject2.data.unit.UnitSend
import com.example.finalproject2.ui.BoardUnit
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Body
import java.util.*
import kotlin.collections.ArrayList

interface RetrofitService {

    @Headers("content-type: application/json")
    @POST("member")
    fun register(
        @Body register : RegisterSend
    ):Call<RegisterResponse>

    @Headers("content-type: application/json")
    @POST("member/login")
    fun login(
        @Body login : LoginSend
    ):Call<LoginResponse>

    @Headers("content-type: application/json")
    @GET("dog-lost/dog-losts")
    fun board(
       @Query("page") page: Int,
       @Query("size") size: Int
    ):Call<Page>

    @Headers("content-type: application/json")
    @POST("Unit")
    fun unit(
        @Body unit : UnitSend
    ):Call<UnitResponse>

    @Multipart
    @POST("dog-found")
    fun found(
        @Part memberId: MultipartBody.Part,
        @Part gender: MultipartBody.Part,
        @Part content: MultipartBody.Part,
        @Part address: MultipartBody.Part,
        @Part files : MultipartBody.Part?
    ):Call<BoardUnit>

    @Multipart
    @POST("dog-lost")
    fun lost(
        @Part memberId: MultipartBody.Part,
        @Part title: MultipartBody.Part,
        @Part dogName: MultipartBody.Part,
        @Part gender: MultipartBody.Part,
        @Part content: MultipartBody.Part,
        @Part address: MultipartBody.Part,
        @Part files : MultipartBody.Part?
    ):Call<BoardUnit>

    @Headers("content-type: application/json")
    @GET("dog-found/85")
    fun alarm(
    ):Call<CommonResponse>
}