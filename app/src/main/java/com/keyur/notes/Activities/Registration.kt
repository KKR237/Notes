package com.keyur.notes.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.keyur.notes.Model.NotesModel
import com.keyur.notes.Model.UserModel
import com.keyur.notes.R

class Registration : AppCompatActivity() {

    private lateinit var txtUsername: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtPass: TextView
    private lateinit var btnSignup: Button
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        txtUsername = findViewById(R.id.usernametxt)
        txtEmail = findViewById(R.id.emailtxt)
        txtPass = findViewById(R.id.passwordtxt)
        btnSignup = findViewById(R.id.btnsignup)
        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        btnSignup.setOnClickListener(){
            signup()
        }
    }

    private fun signup(){
        val name = txtUsername.text.toString()
        val email = txtEmail.text.toString()
        val pass = txtPass.text.toString()
        val uid = dbRef.push().key!!
        val uData = UserModel(uid, name, email,pass)

        if (name.isEmpty() || email.isEmpty() || pass.isEmpty()){
            Toast.makeText(this,"Please enter valid data", Toast.LENGTH_LONG).show()
        } else {
            dbRef.child(uid).setValue(uData)
                .addOnCompleteListener{
                    Toast.makeText(this,"Signup successfully.", Toast.LENGTH_LONG).show()
                    txtUsername.text = ""
                    txtEmail.text = ""
                    txtPass.text = ""
                    val intent = Intent(this, HomeSC::class.java)
                    startActivity(intent)
                }.addOnFailureListener{err ->
                    Toast.makeText(this,"error ${err.message}", Toast.LENGTH_LONG).show()
                }
        }
    }
}