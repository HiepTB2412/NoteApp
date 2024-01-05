package com.example.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.noteapp.Model.NoteModel
import com.example.noteapp.databinding.ActivityAddNoteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSaveNote.setOnClickListener {
            if (binding.title.text.toString().isEmpty()) {
                binding.title.setError("write something")
            }
            if (binding.description.text.toString().isEmpty()) {
                binding.description.setError("write somthing")
            }
            val title = binding.title.text.toString()
            val description = binding.description.text.toString()
            saveNote(title, description)
        }
    }

    private fun saveNote(title: String, description: String) {
        val firebaseAuth = FirebaseAuth.getInstance()
        var id = UUID.randomUUID().toString()
        val note = NoteModel(id, title, description, firebaseAuth.uid!!)
        val firebaseFirestore = FirebaseFirestore.getInstance()

        firebaseFirestore.collection("notes")
            .document(id)
            .set(note)
            .addOnSuccessListener {
                Toast.makeText(this, "Note saved", Toast.LENGTH_LONG).show()
                finish()
            }
            .addOnFailureListener{
                Toast.makeText(this, "Note saved", Toast.LENGTH_LONG).show()

            }
    }

}