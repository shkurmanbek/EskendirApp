package com.example.eskendirapp

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.request.RequestOptions
import com.example.eskendirapp.ModelService
import com.example.eskendirapp.databinding.RowServiceBinding

class AdapterService :RecyclerView.Adapter<AdapterService.HolderService>{
    private val context: Context

    public var serviceArrayList: ArrayList<ModelService>

//    private var filterList:ArrayList<ModelService>

    private lateinit var binding: RowServiceBinding

    constructor(
        context: Context,
        serviceArrayList: ArrayList<ModelService>,
//        filterList: ArrayList<ModelService>
    ) : super() {
        this.context = context
        this.serviceArrayList = serviceArrayList
//        this.filterList = filterList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderService {
        binding = RowServiceBinding.inflate(LayoutInflater.from(context), parent, false)
        return HolderService(binding.root)
    }

    override fun onBindViewHolder(holder: HolderService, position: Int) {
        val model = serviceArrayList[position]
        val id = model.id
        val title1 = model.title1
        val title2 = model.title2
        val typeService = model.typeService

        //set data
        holder.title1.text = title1
        holder.title2.text = title2

//        holder.moreBtn1.setOnClickListener {
//            val intent = Intent(context, ChooseCompany::class.java)
//            intent.putExtra("title1", title1)
//            context.startActivity(intent)
//        }

        holder.moreBtn2.setOnClickListener {
            val intent = Intent(context, ChooseCompany::class.java)
            intent.putExtra("type", position)
            intent.putExtra("title1", title1)
            context.startActivity(intent)
        }
        Glide.with(context)
            .load(model.image)
            .apply(RequestOptions().placeholder(R.drawable.image1).centerCrop())
            .into(holder.image)

    }

    override fun getItemCount(): Int {
        return serviceArrayList.size
    }

    inner class HolderService(itemView: View): RecyclerView.ViewHolder(itemView){
        var title1: TextView = binding.titleTv1
        var title2: TextView = binding.titleTv2
        var image: ImageView = binding.mainIv
        var moreBtn1: ImageButton = binding.moreBtn1
        var moreBtn2: Button = binding.moreBtn2
    }
}