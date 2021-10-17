package com.example.finalproject2.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject2.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BoardAdapter(private val context: Context, private val dataSet: ArrayList<BoardUnit>) : RecyclerView.Adapter<BoardAdapter.ViewHolder>(){

    class ViewHolder(context : Context, view: View, dataSet: ArrayList<BoardUnit>) : RecyclerView.ViewHolder(view){
        val dogimage : ImageView = view.findViewById(R.id.image)
        val dogname : TextView = view.findViewById(R.id.name)
        val doggennder : TextView = view.findViewById(R.id.gender)
        val location : TextView = view.findViewById(R.id.location)
        val date : TextView = view.findViewById(R.id.date)
        init{
            view.setOnClickListener {
                val pos = adapterPosition
                if(pos != RecyclerView.NO_POSITION){
                    val intent = Intent(context,ShowItem::class.java)
                    intent.putExtra("DATA",dataSet[pos])
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.board_unit,viewGroup,false)
        return ViewHolder(context, view, dataSet)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val bitmap = withContext(Dispatchers.IO){
                ImageLoader.loadImage(dataSet[position].fileUris[0])
            }
            viewHolder.dogimage.setImageBitmap(bitmap)
        }
        viewHolder.dogname.text = dataSet[position].dogName
        viewHolder.doggennder.text = dataSet[position].gender
        viewHolder.location.text = dataSet[position].address.si + dataSet[position].address.gu
        viewHolder.date.text = dataSet[position].regDate.toString()
    }

    override fun getItemCount() = dataSet.size
}