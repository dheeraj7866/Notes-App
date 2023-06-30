package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

class AddEditNote : AppCompatActivity() {

    lateinit var addEditNoteTitle:EditText
    lateinit var addEditNoteDescription:EditText
    lateinit var addEditNoteUpdateBtn:Button
    var noteId =-1
    lateinit var viewModel:NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        addEditNoteTitle=findViewById(R.id.addEditTitle)
        addEditNoteDescription=findViewById(R.id.addEditDescription)
        addEditNoteUpdateBtn=findViewById(R.id.addEditUpdateBtn)

        viewModel=ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        val noteType = intent.getStringExtra("noteType")
        if(noteType.equals("Edit")){
            val noteTitle=intent.getStringExtra("noteTitle")
            val noteDes = intent.getStringExtra("noteDescription")
            noteId=intent.getIntExtra("noteID",-1)

            addEditNoteUpdateBtn.setText("Update Note")
            addEditNoteTitle.setText(noteTitle)
            addEditNoteDescription.setText(noteDes)
        }else{
            addEditNoteUpdateBtn.setText("Save Note")
        }

        addEditNoteUpdateBtn.setOnClickListener{
            val noteTitle=addEditNoteTitle.text.toString()
            val noteDes=addEditNoteDescription.text.toString()

            if(noteType.equals("Edit")){
                if(noteTitle.isNotEmpty() && noteDes.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currDate:String=sdf.format(Date())

                    val updateNoteADD = Note(noteTitle,noteDes,currDate)
                    updateNoteADD.id=noteId
                    viewModel.updateNote(updateNoteADD)
                    Toast.makeText(this,"Note Updated...",Toast.LENGTH_LONG).show()
                }
            }else{
                if(noteTitle.isNotEmpty() && noteDes.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currDate: String = sdf.format(Date())
                    viewModel.addNote(Note(noteTitle, noteDes, currDate))
                    Toast.makeText(this, "Note Added...", Toast.LENGTH_LONG).show()
                }

            }
            startActivity(Intent(applicationContext,MainActivity::class.java))
            this.finish()
        }

    }
    override fun onBackPressed() {
        startActivity(Intent(this,MainActivity::class.java))
    }
}