package com.example.finalproject2.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject2.R
import com.example.finalproject2.data.register.UserLocation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.internal.commonAsUtf8ToByteArray

class BoardAdapter(private val context: Context, private var dataSet: ArrayList<BoardUnit>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    inner class ViewHolder(context : Context, view: View, dataSet: ArrayList<BoardUnit>) : RecyclerView.ViewHolder(view){
        val dogimage : ImageView = view.findViewById(R.id.image)
        val dogname : TextView = view.findViewById(R.id.name)
        val doggennder : TextView = view.findViewById(R.id.gender)
        val location : TextView = view.findViewById(R.id.location)
        val date : TextView = view.findViewById(R.id.date)
        fun bind(viewHolder: ViewHolder, position: Int){
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    if (dataSet[position].fileUris[0].length != 0) {
                        val bitmap = withContext(Dispatchers.IO) {
                            ImageLoader.loadImage(dataSet[position].fileUris[0])
                        }
                        viewHolder.dogimage.setImageBitmap(bitmap)
                    }
                }
                catch(e:IndexOutOfBoundsException){

                }
            }
            viewHolder.dogname.text = "이름 :   " + dataSet[position].dogName
            viewHolder.doggennder.text = "성별 :   " + dataSet[position].gender
            viewHolder.location.text =  "지역 :   서울시 "+dataSet[position].address?.gu
            viewHolder.date.text = "등록일자 :   " + dataSet[position].regDate.substring(0,10)
        }
        init{
            view.setOnClickListener {
                val pos = adapterPosition
                if(pos != RecyclerView.NO_POSITION){
                    val intent = Intent(context, ShowItem::class.java)
                    intent.putExtra("DATA",dataSet[pos])
                    context.startActivity(intent)
                }
            }
        }
    }

    inner class LoadingViewHolder(view: View):RecyclerView.ViewHolder(view){}

    override fun getItemViewType(position: Int): Int {
        return when(dataSet[position].id){
            (-1).toLong() -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            VIEW_TYPE_ITEM -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.board_unit,viewGroup,false)
                ViewHolder(context, view, dataSet)
            }
            else -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_loading,viewGroup,false)
                LoadingViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if(viewHolder is ViewHolder){
            viewHolder.bind(viewHolder,position)
        }
        else{}
    }

    override fun getItemCount() = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun dataUpdate(){
        notifyDataSetChanged()
    }
    fun addLoading(){
        dataSet.add(BoardUnit(-1,-1, arrayListOf(-1), arrayListOf(""),"","","","","",
            UserLocation("",""),"",""))
    }
    fun deleteLoading(){
        if(dataSet[dataSet.lastIndex].id == (-1).toLong())
            dataSet.removeAt(dataSet.lastIndex)
    }

}