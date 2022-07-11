package com.example.eskendirapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.core.widget.addTextChangedListener
import com.example.eskendirapp.databinding.ActivityDashboardUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class DashboardUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardUserBinding

    private lateinit var firebaseAuth: FirebaseAuth

    //array list to hold categories

    private lateinit var  serviceArrayList: ArrayList<ModelService>

    private lateinit var adapterService: AdapterService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        loadServices()

        var uid = firebaseAuth.currentUser!!.uid

        binding.profileBtn.setOnClickListener {
            intent = Intent(this@DashboardUserActivity, ProfileActivity::class.java)
            intent.putExtra("uid", uid)
            startActivity(intent)
        }
    }
    private fun loadServices() {
        //init arraylist
        serviceArrayList = ArrayList()
        // get all categories from firebase... Firebase DB > Categories
        val ref = FirebaseDatabase.getInstance().getReference("Services")
        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear list before starting adding data into it
                serviceArrayList.clear()
                for (ds in snapshot.children){
                    var model = ds.getValue(ModelService::class.java)
                    serviceArrayList.add(model!!)
                }
                //setup adapter
                adapterService = AdapterService(this@DashboardUserActivity, serviceArrayList)
                binding.mainRv.adapter = adapterService
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}