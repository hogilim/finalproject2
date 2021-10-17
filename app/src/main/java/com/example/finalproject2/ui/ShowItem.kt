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

        binding.name.text = data.dogName
        binding.gender.text = data.gender
    }
}