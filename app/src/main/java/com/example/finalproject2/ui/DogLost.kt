package com.example.finalproject2.ui

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.example.finalproject2.BuildConfig
import com.example.finalproject2.data.dogbreed
import com.example.finalproject2.data.login.LoginResponse
import com.example.finalproject2.data.register.UserLocation
import com.example.finalproject2.databinding.ActivityCamBinding
import com.example.finalproject2.databinding.ActivityFoundBinding
import com.example.finalproject2.databinding.ActivityLostBinding
import com.example.finalproject2.retrofit2.RetrofitClient
import com.example.finalproject2.retrofit2.RetrofitService
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Multipart
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*
import java.util.Collections.rotate

class DogLost :AppCompatActivity(){
    val retrofit = RetrofitClient.getInstnace() //
    val myAPI = retrofit.create(RetrofitService::class.java)
    var memberId : Long = 0
    private val OPEN_GALLERY = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val location_gu = resources.getStringArray(com.example.finalproject2.R.array.gu)
        val adapter_gu = ArrayAdapter<String>(this, R.layout.simple_list_item_1, location_gu)
        binding.selectGu.adapter = adapter_gu

        memberId = intent.getLongExtra("memberId", -1)

        getImage(binding)
        confirm(binding, location_gu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val binding = ActivityLostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val location_gu = resources.getStringArray(com.example.finalproject2.R.array.gu)
        val adapter_gu = ArrayAdapter<String>(this, R.layout.simple_list_item_1, location_gu)
        binding.selectGu.adapter = adapter_gu

        getImage(binding)
        confirm(binding, location_gu)
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == OPEN_GALLERY){
            val currentImageUrl : Uri? = data?.data
            try{
                val myBitmap = MediaStore.Images.Media.getBitmap(contentResolver,currentImageUrl)
                binding.picture.setImageBitmap(myBitmap)

            }catch (e:Exception){
                e.printStackTrace()
            }
        }
        else{
            Log.d("ActivityResult","Error while getting image")
        }
    }

    private fun getImage(binding: ActivityLostBinding){
        binding.selectPicture.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            startActivityForResult(intent, OPEN_GALLERY)
        }
    }

    private fun confirm(binding: ActivityLostBinding, location_gu:Array<String>){
        var selected_gu = ""
        var selected_breed: Long = 0
        var dgender = "UNDEFINED"
        var isExistBlank = true
        binding.confirm.setOnClickListener {
            binding.selectGu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selected_gu = location_gu[position]
                }
                override fun onNothingSelected(parent: AdapterView<*>?){}
            }
            /*
            if(binding.title.text.toString().isEmpty()||selected_gu==""){
                //Toast.makeText(this, "?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show()
                isExistBlank = true
            }
            else{
                isExistBlank = false
            }*/
            val radioGroup = binding.gender
            val intSelectButton: Int = radioGroup.checkedRadioButtonId
            val radioButton = findViewById<View>(intSelectButton) as RadioButton
            dgender = radioButton.text.toString()


            if(isExistBlank){

                val memberId = MultipartBody.Part.createFormData("memberId", memberId.toString())
                val title = MultipartBody.Part.createFormData("title", binding.title.text.toString())
                val gender = MultipartBody.Part.createFormData("gender", dgender)
                val content = MultipartBody.Part.createFormData("content", binding.content.text.toString())
                val dogName = MultipartBody.Part.createFormData("dogName", binding.name.text.toString())
                val address = MultipartBody.Part.createFormData("address", binding.selectGu.selectedItem.toString())

                println("memberId = ${memberId}")
                println("dogname!${dogName} ${binding.name.text.toString()}")
                println("title = ${title}")
                println("gender!! = ${dgender}${binding.gender.checkedRadioButtonId}")
                println("content = ${content}")

                val t = binding.picture.getDrawable() as BitmapDrawable
                val b = t.bitmap
                val ba = ByteArrayOutputStream()
                b.compress(Bitmap.CompressFormat.JPEG, 20, ba)
                val rb = RequestBody.create("image/jpg".toMediaTypeOrNull(),ba.toByteArray())
                val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val fileName = "photo" + timestamp +".jpeg"
                val u = MultipartBody.Part.createFormData("files",fileName ,rb)
                myAPI.lost(
                    memberId,
                    title,
                    dogName,
                    gender,
                    content,
                    address,
                    u
                ).enqueue(object : Callback<BoardUnit> {

                    //?????? onFaliure??? Call??? ??????????????? ?????? ????????? ?????? ???????????????.
                    override fun onFailure(call: Call<BoardUnit>, t: Throwable) {
                        Toast.makeText(this@DogLost,"?????? ?????? ???????????????.", Toast.LENGTH_SHORT).show()
                        println("DogLost.onFailure -----")
                        println(t.message)
                    }

                    //?????? ?????? ?????? ???????????? ????????? resonse??? ????????? ???????????????.
                    //????????? call??? ?????? ??? RawResponseData??? ???????????? Reponse??? ??? ????????? ????????? ?????????.
                    override fun onResponse(
                        call: Call<BoardUnit>,
                        response: Response<BoardUnit>
                    ) {

                        println("response : ${response.errorBody()}")
                        println("response : ${response.message()}")
                        println("response : ${response.code()}")
                        println("response : ${response.raw().request.url.toUrl()}")
                        println("response : ${response.body()}")

                    }
                })
                Toast.makeText(this@DogLost, "?????? ??????.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }





}