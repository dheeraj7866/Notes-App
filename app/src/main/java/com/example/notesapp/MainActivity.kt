package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteClickDeleteInterface, NoteClickInterface {

    lateinit var notesRv:RecyclerView
    lateinit var addFAB:FloatingActionButton
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notesRv=findViewById(R.id.recyclerView)
        addFAB=findViewById(R.id.floatingActionButton)

        notesRv.layoutManager=LinearLayoutManager(this)

        val noteAdapter = NoteAdapter(this,this,this)
        notesRv.adapter=noteAdapter
        viewModel=ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer { list->
            list?.let {
                noteAdapter.updateList(it)
            }
        })
        addFAB.setOnClickListener{
            val intent = Intent(this@MainActivity,AddEditNote::class.java)
            startActivity(intent)
            this.finish()
        }

    }

    override fun onClickDeleteIcon(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this,"${note.noteTitle} Deleted",Toast.LENGTH_LONG).show()
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this@MainActivity,AddEditNote::class.java)
        intent.putExtra("noteType","Edit")
        intent.putExtra("noteTitle",note.noteTitle)
        intent.putExtra("noteDescription",note.noteDescription)
        intent.putExtra("noteID",note.id)
        startActivity(intent)
        this.finish()

    }
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity() // Finish the activity and close the app
    }
}