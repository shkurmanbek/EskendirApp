package com.example.eskendirapp

import android.R
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.eskendirapp.databinding.ActivityRegisterInsurnaceBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class InsurnaceActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityRegisterInsurnaceBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    var list_of_items = arrayOf("6 месяцев", "12 месяцев", "24 месяца")
    var spinner: Spinner? = null
    var textView_msg: TextView? = null
    var price1: Int = 0
    var typeCompany: Int = 0
    var month1: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterInsurnaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.registerBtn.setOnClickListener {
            validateData()
        }

        intent.getStringExtra("price")
        price1 = intent.getIntExtra("price1", 0)

        typeCompany = intent.getIntExtra("typeCompany", 0)

        textView_msg = binding.msg
        spinner = binding.planetsSpinner
        spinner!!.setOnItemSelectedListener(this)

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this, R.layout.simple_spinner_item, list_of_items)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.setAdapter(aa)
    }
    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        textView_msg!!.text = "Срок : "+list_of_items[position]
        var editable :Editable?= null
        var res = 0
        if(position==0){
            res = price1 * 6
            editable = Editable.Factory.getInstance().newEditable("$res")
            month1 = 6
        } else if(position==1) {
            res = price1 * 12
            editable = Editable.Factory.getInstance().newEditable("$res")
            month1 = 12
        } else if(position==2) {
            res = price1 * 24
            editable = Editable.Factory.getInstance().newEditable("$res")
            month1 = 24
        }
        binding.costEt.text = editable
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

    private var name = ""
    private var IIN = ""
    private var age = ""
    private var city = ""
    private var cost = ""
    private fun validateData() {
        name = binding.nameEt.text.toString().trim()
        age = binding.ageEt.text.toString().trim()
        city = binding.cityEt.text.toString().trim()
        cost = binding.costEt.text.toString().trim()
        IIN = binding.IINet.text.toString().trim()
        if(name.isEmpty()){
            Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show()
        } else if (city.isEmpty()){
            Toast.makeText(this, "Enter Password...", Toast.LENGTH_SHORT).show()
        } else if (cost.isEmpty()){
            Toast.makeText(this, "Enter mobile...", Toast.LENGTH_SHORT).show()
        } else if (age.isEmpty()){
            Toast.makeText(this, "Enter mobile...", Toast.LENGTH_SHORT).show()
        } else if (IIN.isEmpty()){
            Toast.makeText(this, "Enter mobile...", Toast.LENGTH_SHORT).show()
        }
        else {
            updateUserInfo(typeCompany)
        }
    }

    private fun updateUserInfo(type: Int) {
        progressDialog.setMessage("Saving user info...")

        val timestamp = System.currentTimeMillis()

        val uid = firebaseAuth.uid

        val hashMap: HashMap<String, Any?> = HashMap()

        hashMap["uid"] = uid
        hashMap["cost"] = cost
        hashMap["name"] = name
        hashMap["age"] = age
        hashMap["city"] = city
        hashMap["IIN"] = IIN
        hashMap["month"] = month1
        hashMap["timestamp"] = timestamp
        hashMap["typeCompany"] = type

        val ref = FirebaseDatabase.getInstance().getReference("Request")
        ref.child("$uid")
            .updateChildren(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Created Successfully...", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,DashboardUserActivity::class.java))
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to Create Account due to${e.message}...", Toast.LENGTH_SHORT).show()
            }
    }
}