package com.geekbrains.androidstart.notes.ui

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.geekbrains.androidstart.notes.R
import com.geekbrains.androidstart.notes.databinding.FragmentNoteBinding
import com.geekbrains.androidstart.notes.pojo.Note
import com.geekbrains.androidstart.notes.utils.SaveNote

// Урок 11
// 1. Сделайте фрагмент добавления и редактирования данных, если вы ещё не сделали его.

class NoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding
    private lateinit var navController: NavController
    private lateinit var sn: SaveNote
    private var oldNoteName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNote()
        sn = SaveNote(requireContext())
        navController = Navigation.findNavController(view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar_menu, menu)
    }

    // Урок 12
    // Пример работы со списком заметок

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_note -> {
                sn.saveNote(
                    requireContext(), oldNoteName,
                    binding.tvCurrentNoteName.text.toString(),
                    binding.tvCurrentNoteDescription.text.toString()
                )
                navController.popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupEditTextOnChanged(name: String, description: String) {
        binding.tvCurrentNoteName.doOnTextChanged { text, _, _, _ ->
            if (text?.equals(name) == false) setHasOptionsMenu(true)
        }
        binding.tvCurrentNoteDescription.doOnTextChanged { text, _, _, _ ->
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