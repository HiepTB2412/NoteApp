package com.example.noteapp.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.Model.NoteModel
import com.example.noteapp.R
import com.example.noteapp.UpdateActivity

class NoteAdapter(val ds: List<NoteModel>):RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.txtTitle)
        val description: TextView = itemView.findViewById(R.id.txtDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_tow, parent,false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.apply {
            title.text = ds[position].title
            description.text = ds[position].description
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, UpdateActivity::class.java)
            intent.putExtra("id", ds[position].id)
            intent.putExtra("title", ds[position].title)
            intent.putExtra("description", ds[position].description)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return ds.size
    }
}