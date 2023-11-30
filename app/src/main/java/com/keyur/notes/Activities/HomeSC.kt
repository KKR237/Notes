package com.keyur.notes.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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
//        rcylView.layoutManager = LinearLayoutManager(this)

//        rcylView.apply {
//            layoutManager = GridLayoutManager(this@HomeSC, 2)
//        }

        // set a StaggeredGridLayoutManager with 3 number of columns and horizontal orientation
        // set a StaggeredGridLayoutManager with 3 number of columns and horizontal orientation
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        rcylView.setLayoutManager(staggeredGridLayoutManager)

        rcylView.setHasFixedSize(true)

        noteList = arrayListOf<NotesModel>()
        getNoteData()

        btnAdd = findViewById(R.id.btnAdd)
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
                            intent.putExtra("by",noteList[position].by)
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