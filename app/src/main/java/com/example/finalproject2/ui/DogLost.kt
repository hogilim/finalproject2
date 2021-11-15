package com.example.finalproject2.ui

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.example.finalproject2.BuildConfig
import com.example.finalproject2.databinding.ActivityCamBinding
import com.example.finalproject2.databinding.ActivityFoundBinding
import com.example.finalproject2.databinding.ActivityLostBinding
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class DogLost :AppCompatActivity(){
    private val OPEN_GALLERY = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val location_gu = resources.getStringArray(com.example.finalproject2.R.array.gu)
        val adapter_gu = ArrayAdapter<String>(this, R.layout.simple_list_item_1, location_gu)
        binding.selectGu.adapter = adapter_gu
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
                val mybitmap = MediaStore.Images.Media.getBitmap(contentResolver,currentImageUrl)
                binding.picture.setImageBitmap(mybitmap)
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
        var gender = "unknown"
        var isExistBlank = false
        var dogname = ""
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
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
            if(binding.title.text.toString().isEmpty()||selected_gu==""){
                Toast.makeText(this, "모든 정보를 작성해주세요.", Toast.LENGTH_SHORT).show()
                isExistBlank = true
            }
            else{
                isExistBlank = false
            }
            binding.gender.setOnCheckedChangeListener { group, checkedId ->
                when(checkedId) {
                    binding.male.id->{
                        gender = "male"
                    }
                    binding.female.id->{
                        gender = "female"
                    }
                    else -> {
                        gender = "unknown"
                    }
                }
            }
            if(!isExistBlank){
                // retrofit 전송 후 리턴
                Toast.makeText(this@DogLost, "신고 성공.${gender}", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }




}