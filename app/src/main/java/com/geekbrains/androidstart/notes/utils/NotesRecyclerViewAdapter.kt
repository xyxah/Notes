package com.geekbrains.androidstart.notes.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.androidstart.notes.databinding.NoteItemBinding
import com.geekbrains.androidstart.notes.pojo.Note

class NotesRecyclerViewAdapter(private val notes: List<Note>) :
    RecyclerView.Adapter<NotesRecyclerViewAdapter.NoteViewHolder>() {

    var onClick: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemBinding =
            NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(itemBinding)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note: Note = notes[position]
        holder.bind(note)

        holder.itemView.setOnClickListener {
            onClick?.onNoteClick(note)
        }

        holder.itemView.setOnLongClickListener {
            onClick?.onNoteLongClick(holder.itemView, note)
            false
        }
    }

    inner class NoteViewHolder(private val itemBinding: NoteItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(note: Note) {
            itemBinding.tvNoteName.text = note.name
            itemBinding.tvNoteDate.text = note.date
        }
    }

    interface OnClickListener {
        fun onNoteClick(note: Note)
        fun onNoteLongClick(view: View, note: Note)
    }
}