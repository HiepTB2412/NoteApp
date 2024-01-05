package com.example.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.noteapp.Adapter.NoteAdapter
import com.example.noteapp.Model.NoteModel
import com.example.noteapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddNote.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        val firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser === null) {
            firebaseAuth.signInAnonymously()
                .addOnSuccessListener {

                }
                .addOnFailureListener {
                    Toast.makeText(this, "loi", Toast.LENGTH_LONG).show()
                }
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        FirebaseFirestore.getInstance().collection("notes")
            .whereEqualTo("uuid", FirebaseAuth.getInstance().uid)
            .get()
            .addOnSuccessListener { result ->
                val ds = mutableListOf<NoteModel>()
                ds.clear()
                for (i in result) {
                    val description = i.getString("description")
                    val id = i.getString("id")
                    val title = i.getString("title")
                    val uuid = i.getString("uuid")

                    val note = NoteModel(id.toString(),title.toString(), description.toString(), uuid.toString())
                    ds.add(note)
                }
                val adapter = NoteAdapter(ds)
                binding.rvNote.adapter = adapter
                binding.rvNote.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            }
            .addOnFailureListener {
                Toast.makeText(this, "loi", Toast.LENGTH_LONG).show()
            }
    }
}