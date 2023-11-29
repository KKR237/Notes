package com.keyur.notes.Activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.keyur.notes.Adapter.NoteAdapter
import com.keyur.notes.Model.NotesModel
import com.keyur.notes.R

class HomeSC : AppCompatActivity() {
    private lateinit var rcylView: RecyclerView
    private lateinit var btnAdd: FloatingActionButton
    private lateinit var noteList:ArrayList<NotesModel>
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_sc)

        rcylView = findViewById(R.id.recyclerView)
        rcylView.layoutManager = LinearLayoutManager(this)
        rcylView.setHasFixedSize(true)

        noteList = arrayListOf<NotesModel>()
        getNoteData()

        btnAdd = findViewById(R.id.btnAdd)
        val myColor: Int = Color.parseColor("#BB9BE4")
        btnAdd.setBackgroundColor(myColor)
        btnAdd.setOnClickListener{
            var intent = Intent(this, AddNote::class.java)
            startActivity(intent)
        }
    }

    private fun getNoteData() {
        dbRef = FirebaseDatabase.getInstance().getReference("Notes")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                noteList.clear()
                if (snapshot.exists()){
                    for (notesnap in snapshot.children){
                        val noteData = notesnap.getValue(NotesModel::class.java)
                        noteList.add(noteData!!)
                    }
                    val nAdapter = NoteAdapter(noteList)
                    rcylView.adapter = nAdapter

                    nAdapter.setOnItemClickListener(object :NoteAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            var intent = Intent(this@HomeSC, ViewNote::class.java)

                            //put extras
                            intent.putExtra("noteId",noteList[position].noteId)
                            intent.putExtra("title",noteList[position].title)
                            intent.putExtra("note",noteList[position].note)
                            startActivity(intent)
                        }

                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}