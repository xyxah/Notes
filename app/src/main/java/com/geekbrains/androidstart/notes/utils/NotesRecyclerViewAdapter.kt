package com.geekbrains.androidstart.notes.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.androidstart.notes.R
import com.geekbrains.androidstart.notes.pojo.Note
import kotlinx.android.synthetic.main.note_item.view.*

//1. Создайте список ваших заметок

class NotesRecyclerViewAdapter(private val notes: ArrayList<Note>?) : RecyclerView.Adapter<NotesRecyclerViewAdapter.NoteViewHolder>() {

    var onClick : OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.note_item,
            parent,
            false
        )
        return NoteViewHolder(v)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = notes!!.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes!![position]
        holder.noteName.text = note.name
        holder.noteDate.text = note.date

        holder.itemView.setOnClickListener {
            onClick?.onNoteClick(note)
        }
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteName: TextView = itemView.tv_note_name
        val noteDate: TextView = itemView.tv_note_date
    }

    interface OnClickListener{
        fun onNoteClick(note:Note)
    }
}