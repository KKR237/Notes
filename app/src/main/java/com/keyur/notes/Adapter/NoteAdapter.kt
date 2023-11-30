package com.keyur.notes.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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
        holder.tvBy.text = "By : ${note.by}"

        val colors: Array<String> = arrayOf(
            "#ffafcc","#bde0fe","#a2d2ff",
            "#2a9d8f","#d1b3c4","#e9c46a",
            "#f4a261","#e76f51","#f28482",
            "#84a59d","#f5cac3","#d0f4de",
            "#a9def9","#c77dff","#d8f3dc")
        val colorList = ArrayList<Int>()

        // add the color array to the list
        for (i in colors.indices) {
            colorList.add(Color.parseColor(colors[i]))
        }
        holder.bgView.setCardBackgroundColor(colorList.random())
    }

    override fun getItemCount(): Int {
        return notelist.size
    }

    class ViewHolder(itemView:View,clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val tvTitle:TextView = itemView.findViewById(R.id.txtTitle)
        val tvNote:TextView = itemView.findViewById(R.id.txtNote)
        val tvBy:TextView = itemView.findViewById(R.id.txtBy)
        val bgView:CardView = itemView.findViewById(R.id.card)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}