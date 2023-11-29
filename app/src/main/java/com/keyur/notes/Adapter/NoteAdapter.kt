package com.keyur.notes.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.keyur.notes.Model.NotesModel
import com.keyur.notes.R

class NoteAdapter(private val notelist:ArrayList<NotesModel>):RecyclerView.Adapter<NoteAdapter.ViewHolder>(){
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_list,parent,false)
        return ViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notelist[position]
        holder.tvTitle.text = note.title
        holder.tvNote.text = note.note
    }

    override fun getItemCount(): Int {
        return notelist.size
    }

    class ViewHolder(itemView:View,clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val tvTitle:TextView = itemView.findViewById(R.id.txtTitle)
        val tvNote:TextView = itemView.findViewById(R.id.txtNote)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}