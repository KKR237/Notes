package com.keyur.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private val notelist:ArrayList<NotesModel>):RecyclerView.Adapter<NoteAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_list,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteAdapter.ViewHolder, position: Int) {
        val note = notelist[position]
        holder.tvTitle.text = note.title
        holder.tvNote.text = note.note
    }

    override fun getItemCount(): Int {
        return notelist.size
    }

    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle:TextView = itemView.findViewById(R.id.txtTitle)
        val tvNote:TextView = itemView.findViewById(R.id.txtNote)
    }
}