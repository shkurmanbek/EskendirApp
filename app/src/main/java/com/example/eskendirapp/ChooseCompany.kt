package com.example.eskendirapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eskendirapp.databinding.ActivityChooseCompanyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChooseCompany : AppCompatActivity() {
    lateinit var binding: ActivityChooseCompanyBinding

    private lateinit var firebaseAuth: FirebaseAuth

    //array list to hold categories

    private lateinit var companyArrayList: ArrayList<ModelCompany>
    private var type: Int ?= 0

    private lateinit var adapterCompany: AdapterCompany

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title1 = intent.getStringExtra("title1")
        type = intent.getIntExtra("type", 0)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.titleTv1.text = title1

        firebaseAuth = FirebaseAuth.getInstance()

        loadCompanies()
        updateInfoDb(type!!)
    }

    private fun loadCompanies() {
        //init arraylist
        companyArrayList = ArrayList()
        // get all categories from firebase... Firebase DB > Categories
        val ref = FirebaseDatabase.getInstance().getReference("Companies")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear list before starting adding data into it
                companyArrayList.clear()
                for (ds in snapshot.children){
                    var model = ds.getValue(ModelCompany::class.java)
                    companyArrayList.add(model!!)
                }
                //setup adapter
                adapterCompany = AdapterCompany(this@ChooseCompany, companyArrayList)
                binding.mainRv.adapter = adapterCompany
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun updateInfoDb(type: Int){
        val uid = firebaseAuth.currentUser!!.uid
        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["typeService"] = type
        val ref = FirebaseDatabase.getInstance().getReference("Orders")
        ref.child(uid).updateChildren(hashMap)
    }
}