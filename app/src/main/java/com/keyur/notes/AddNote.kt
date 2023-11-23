package com.keyur.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddNote : AppCompatActivity() {

    private lateinit var txtTitle:TextView
    private lateinit var txtNote:TextView
    private lateinit var btnSubmit:Button
    private lateinit var dbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        txtTitle = findViewById(R.id.txTitle)
        txtNote = findViewById(R.id.txNote)
        btnSubmit = findViewById(R.id.btnSubmit)

        dbRef = FirebaseDatabase.getInstance().getReference("Notes")



        btnSubmit.setOnClickListener(){
            saveNotes()
        }
    }

    private fun saveNotes(){
        val title = txtTitle.text.toString()
        val note = txtNote.text.toString()
        val noteId = dbRef.push().key!!
        var notes = NotesModel(noteId, title, note)

        if (title.isEmpty() || note.isEmpty()){
            Toast.makeText(this,"Please enter valid data",Toast.LENGTH_LONG).show()
        } else {
            dbRef.child(noteId).setValue(notes)
                .addOnCompleteListener{
                    Toast.makeText(this,"Data save successfully.",Toast.LENGTH_LONG).show()
                    txtTitle.text = ""
                    txtNote.text = ""
//                    finish()
                }.addOnFailureListener{err ->
                    Toast.makeText(this,"error ${err.message}",Toast.LENGTH_LONG).show()
                }
        }
    }
}