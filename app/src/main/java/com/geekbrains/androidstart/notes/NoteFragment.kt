package com.geekbrains.androidstart.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class NoteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNote()
    }

    private fun initNote() {
        if (arguments != null) {
            val note: Note? = requireArguments().getParcelable("note")
            if (note != null) {
                val tvName: TextView? = view?.findViewById(R.id.tv_current_note_name)
                if (tvName != null) tvName.text = note.name

                val tvDescription: TextView? = view?.findViewById(R.id.tv_current_note_description)
                if (tvDescription != null) tvDescription.text = note.description
            }
        }
    }
}