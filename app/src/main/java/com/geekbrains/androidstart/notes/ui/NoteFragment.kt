package com.geekbrains.androidstart.notes.ui

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.geekbrains.androidstart.notes.MainActivity
import com.geekbrains.androidstart.notes.R
import com.geekbrains.androidstart.notes.databinding.FragmentNoteBinding
import com.geekbrains.androidstart.notes.pojo.Note

class NoteFragment : Fragment() {

    //4. * Создайте фрагмент для редактирования данных в конкретной карточке.

    private lateinit var binding: FragmentNoteBinding
    private lateinit var oldNoteName: String

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_note -> {
                (context as MainActivity).saveNote(
                    oldNoteName,
                    binding.tvCurrentNoteName,
                    binding.tvCurrentNoteDescription
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupEditTextOnChanged(name: String, description: String) {
        binding.tvCurrentNoteName.doOnTextChanged { text, start, before, count ->
            if (text?.equals(name) == false) setHasOptionsMenu(true)
        }
        binding.tvCurrentNoteDescription.doOnTextChanged { text, start, before, count ->
            if (text?.equals(description) == false) setHasOptionsMenu(true)
        }
    }

    private fun initNote() {
        arguments?.getParcelable<Note>("note")?.let { note ->
            val tvName: TextView? = view?.findViewById(R.id.tv_current_note_name)
            val tvDescription: TextView? = view?.findViewById(R.id.tv_current_note_description)

            oldNoteName = note.name

            tvName?.text = note.name
            tvDescription?.text = note.description

            setupEditTextOnChanged(note.name, note.description)
        }
    }
}