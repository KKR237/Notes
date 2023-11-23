package com.keyur.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var rcylView:RecyclerView
    private lateinit var btnAdd:Button
    private lateinit var tvLoading:TextView
    private lateinit var noteList:ArrayList<NotesModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvLoading = findViewById(R.id.txtLoading)
        rcylView = findViewById(R.id.recyclerView)
        rcylView.layoutManager = LinearLayoutManager(this)
        rcylView.setHasFixedSize(true)

        noteList = arrayListOf<NotesModel>()
        getNoteData()

        btnAdd = findViewById<Button>(R.id.btnAdd)
        btnAdd.setOnClickListener(){
            var intent = Intent(this, AddNote::class.java)
            startActivity(intent)
        }
    }

    private fun getNoteData() {
        rcylView.visibility = View.GONE
        tvLoading.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Notes")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                noteList.clear()
                if (snapshot.exists()){
                    for (notesnap in snapshot.children){
                        val noteData = notesnap.getValue(NotesModel::class.java)
                        noteList.add(noteData!!)
                    }
                    val nAdapter = NoteAdapter(noteList)
                    rcylView.adapter = nAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}