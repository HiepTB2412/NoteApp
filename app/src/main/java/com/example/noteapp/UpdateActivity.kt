package com.example.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.noteapp.Model.NoteModel
import com.example.noteapp.databinding.ActivityUpdateBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var id: String
    private lateinit var title: String
    private lateinit var description: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra("id")!!
        title = intent.getStringExtra("title")!!
        description = intent.getStringExtra("description")!!
        binding.title.setText(title)
        binding.description.setText(description)
        binding.btnSaveNote.setOnClickListener {
            if (binding.title.text.toString().isEmpty()) {
                binding.title.setError("write something")
            }
            if (binding.description.text.toString().isEmpty()) {
                binding.description.setError("write somthing")
            }
            val title = binding.title.text.toString()
            val description = binding.description.text.toString()
            updateNote(title, description)
        }

        binding.btnDelete.setOnClickListener {
            FirebaseFirestore.getInstance()
                .collection("notes")
                .document(id).delete()
            finish()
        }
    }

    private fun updateNote(title: String, description: String) {
        val firebaseAuth = FirebaseAuth.getInstance()
        val note = NoteModel(id, title, description, firebaseAuth.uid!!)
        val firebaseFirestore = FirebaseFirestore.getInstance()

        firebaseFirestore.collection("notes")
            .document(id)
            .set(note)
            .addOnSuccessListener {
                Toast.makeText(this, "Note saved", Toast.LENGTH_LONG).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Note saved", Toast.LENGTH_LONG).show()

            }
    }
}