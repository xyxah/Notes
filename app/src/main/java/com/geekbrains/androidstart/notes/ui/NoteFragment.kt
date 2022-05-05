package com.geekbrains.androidstart.notes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.geekbrains.androidstart.notes.R
import com.geekbrains.androidstart.notes.databinding.FragmentNoteBinding
import com.geekbrains.androidstart.notes.pojo.Note

class NoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNote()
    }

    private fun initNote() {
        arguments?.getParcelable<Note>("note")?.let { note ->
            val tvName: TextView? = view?.findViewById(R.id.tv_current_note_name)
            val tvDescription: TextView? = view?.findViewById(R.id.tv_current_note_description)

            tvName?.text = note.name
            tvDescription?.text = note.description
        }
    }
}