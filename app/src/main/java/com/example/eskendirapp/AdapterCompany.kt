package com.example.eskendirapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.eskendirapp.databinding.RowCompanyBinding

class AdapterCompany : RecyclerView.Adapter<AdapterCompany.HolderCompany> {

    private val context: Context
    public var companyArrayList: ArrayList<ModelCompany>

    private lateinit var binding: RowCompanyBinding

    constructor(
        context: Context,
        companyArrayList: ArrayList<ModelCompany>,
    ) : super() {
        this.context = context
        this.companyArrayList = companyArrayList
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HolderCompany{
        binding = RowCompanyBinding.inflate(LayoutInflater.from(context), parent, false)
        return HolderCompany(binding.root)
    }
    inner class HolderCompany(itemView: View): RecyclerView.ViewHolder(itemView){
        var price: TextView = binding.titleTv2
        var name: TextView = binding.titleTv1
        var star: ImageView = binding.starIv
        var logo: ImageView = binding.mainIv
        var moreBtn: ImageView = binding.moreBtn
    }

    override fun onBindViewHolder(holder: HolderCompany, position: Int) {
        val model = companyArrayList[position]
        val id = model.id
        val title1 = model.title1
        val title2 = model.title2
        val price = model.price
        val type = model.type

        //set data
        holder.name.text = title1
        holder.price.text = title2

        holder.moreBtn.setOnClickListener {
            val intent = Intent(context, InsurnaceActivity::class.java)
            intent.putExtra("price", title2)
            intent.putExtra("price1", price)
            intent.putExtra("typeCompany", position)
            context.startActivity(intent)
        }
        Glide.with(context)
            .load(model.mainIv)
            .apply(RequestOptions().placeholder(R.drawable.company1).centerCrop())
            .into(holder.logo)

        Glide.with(context)
            .load(model.starIv)
            .apply(RequestOptions().placeholder(R.drawable.stars).centerCrop())
            .into(holder.star)
    }

    override fun getItemCount(): Int {
        return companyArrayList.size
    }
}