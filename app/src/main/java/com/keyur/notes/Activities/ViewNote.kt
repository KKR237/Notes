package com.keyur.notes.Activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.keyur.notes.R

class ViewNote : AppCompatActivity() {
    private lateinit var txtTitle: TextView
    private lateinit var txtNote: TextView
    private lateinit var txtBy: TextView
    private lateinit var btnDlt: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_note)

        initView()
        setValuesToViews()

        btnDlt.setOnClickListener{
            showAlert()
        }
    }

    private fun initView() {
        txtTitle = findViewById(R.id.title)
        txtNote = findViewById(R.id.note)
        txtBy = findViewById(R.id.by)
        btnDlt = findViewById(R.id.btnDlt)
    }

    private fun setValuesToViews() {
        txtTitle.text = intent.getStringExtra("title")
        txtNote.text = intent.getStringExtra("note")
        txtBy.text = "By : ${intent.getStringExtra("by")}"
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("QUICK NOTE")
            .setMessage("Do you want to Delete this Note?")
            .setPositiveButton("Yes"){
                dialog,which->
                deleteRecord(intent.getStringExtra("noteId").toString())
            }
            .setNegativeButton("No"){
                dialog,which->
                dialog.dismiss()
            }
        val alertDialog:AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun deleteRecord(id:String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Notes").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnCompleteListener{
            Toast.makeText(this,"Note Deleted", Toast.LENGTH_LONG).show()
            finish()
        }.addOnFailureListener{
            err ->
            Toast.makeText(this,"Error deleting note ${err.toString()}", Toast.LENGTH_LONG).show()
        }
    }
}