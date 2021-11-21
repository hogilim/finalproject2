package com.example.finalproject2.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject2.databinding.ActivityShowitemBinding

class ShowItem : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityShowitemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showData(binding)

    }
    private fun showData(binding: ActivityShowitemBinding){
        val data : BoardUnit = intent.getSerializableExtra("DATA") as BoardUnit
        for (i in data.fileUris)
            println(i)
        val Images = data.fileUris

        val madapter = ShowItemImageAdapter(Images)
        binding.dogimagelist.adapter = madapter

        binding.title.text = "제목 :   " + data.title
        binding.name.text = "이름 :   " + data.dogName
        binding.gender.text = "성별 :   " + data.gender
        binding.content.text = data.content
        binding.date.text = data.regDate.substring(0,10)
    }
}