package com.example.finalproject2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject2.R

class BoardAdapter(private val dataSet: ArrayList<BoardUnit>) : RecyclerView.Adapter<BoardAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val dogimage : ImageView = view.findViewById(R.id.image)
        val dogname : TextView = view.findViewById(R.id.name)
        val doggennder : TextView = view.findViewById(R.id.gender)
        val location : TextView = view.findViewById(R.id.location)
        val date : TextView = view.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.board_unit,viewGroup,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.dogimage.setImageBitmap(dataSet[position].imgProfile)
        viewHolder.dogname.text = dataSet[position].name
        viewHolder.doggennder.text = dataSet[position].gender
        viewHolder.location.text = dataSet[position].location.si + dataSet[position].location.gu
        viewHolder.date.text = dataSet[position].date

    }

    override fun getItemCount() = dataSet.size
}