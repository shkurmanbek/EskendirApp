package com.example.eskendirapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.eskendirapp.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var userId: String
    private lateinit var firebaseAuth: FirebaseAuth
//    private lateinit var ordersArrayList: ArrayList<Order>
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        userId = intent.getStringExtra("uid").toString()
        binding.titleTv6.text = "Эл. почта: "+ firebaseAuth.currentUser!!.email
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        val userRef = FirebaseDatabase.getInstance().getReference("Users")
        val ordersRef = userRef.child(userId)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.titleTv2.text = "ФИО: " + snapshot.child("name").getValue(String::class.java)
                binding.titleTv5.text = "ИИН: " + snapshot.child("IIN").getValue(String::class.java)
                binding.titleTv7.text = "Мобильный Номер: " + snapshot.child("mobile").getValue(String::class.java)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Data", error.getMessage())
            }
        }
        ordersRef.addValueEventListener(valueEventListener)

        val user1Ref = FirebaseDatabase.getInstance().getReference("Orders")
        val orders1Ref = user1Ref.child(userId)
        var typeService = 0
        var typeCompany = 0
        val valueEventListener1 = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            binding.titleTv3.text = "Возраст: " + snapshot.child("age").getValue(String::class.java)
            binding.titleTv4.text = "Город: " + snapshot.child("city").getValue(String::class.java)
            binding.titleTv8.text = "Выплата: " + snapshot.child("cost").getValue(String::class.java)
            typeCompany = snapshot.child("typeCompany").getValue(Int::class.java)!!
            typeService = snapshot.child("typeService").getValue(Int::class.java)!!
            if(typeService==0){
                binding.titleTv10.text = "Страхование авто"
            } else if (typeService==1){
                binding.titleTv10.text = "Страхование квартир"
            } else if (typeService==2){
                binding.titleTv10.text = "Страхование дома"
            } else if (typeService==3){
                binding.titleTv10.text = "Страхование от несчастного случая"
            }

            if(typeCompany==0){
                binding.titleTv11.text = "Компания: Jusan Garant"
            } else if (typeCompany==1){
                binding.titleTv11.text = "Компания: Freedom Insurance "
            } else if (typeCompany==2){
                binding.titleTv11.text = "Компания:  Amanat 24"
            } else if (typeCompany==3){
                binding.titleTv11.text = "Компания:  Nat Insurance"
            } else if (typeCompany==4){
                binding.titleTv11.text = "Компания:  HCK Group"
            } else if (typeCompany==5){
                binding.titleTv11.text = "Компания: Евразия"
            }

            binding.titleTv9.text = "Срок на " + snapshot.child("month").getValue(Int::class.java).toString() + " месяцев"
        }
        override fun onCancelled(error: DatabaseError) {
            Log.d("Data", error.getMessage())
        }
        }
    orders1Ref.addValueEventListener(valueEventListener1)
    }

//    private fun initUser() {
//        //init arraylist
//        ordersArrayList = ArrayList()
//        // get all categories from firebase... Firebase DB > Categories
//        val ref = FirebaseDatabase.getInstance().getReference("Orders")
//        ref.addValueEventListener(object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                //clear list before starting adding data into it
//                ordersArraylist.clear()
//                for (ds in snapshot.children){
//                    var model = ds.getValue(ModelService::class.java)
//                    serviceArrayList.add(model!!)
//                }
//                //setup adapter
//                adapterService = AdapterService(this@DashboardUserActivity, serviceArrayList)
//                binding.mainRv.adapter = adapterService
//            }
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
//    }
}