package com.example.notesapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(
    val context: Context,
    val onNoteClickDeleteInterface: NoteClickDeleteInterface,
    val onNoteClickInterface: NoteClickInterface
                  ): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    private val allNotes=ArrayList<Note>()

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val noteTitle = itemView.findViewById<TextView>(R.id.notesTitle)
        val timeStamp = itemView.findViewById<TextView>(R.id.timeStamp)
        val deleteBtn = itemView.findViewById<ImageView>(R.id.delete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.notes_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.noteTitle.setText(allNotes.get(position).noteTitle)
        holder.timeStamp.setText("Last Updated : "+allNotes.get(position).timeStamp)
        holder.deleteBtn.setOnClickListener{
            onNoteClickDeleteInterface.onClickDeleteIcon(allNotes.get(position))
        }

        holder.itemView.setOnClickListener{
            onNoteClickInterface.onNoteClick(allNotes.get(position))


        }
    }

    fun updateList(newList:List<Note>){
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }

}



interface NoteClickDeleteInterface {
    fun onClickDeleteIcon(note: Note)
}
interface NoteClickInterface {
    fun onNoteClick(note: Note)
}