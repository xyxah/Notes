package com.geekbrains.androidstart.notes.ui

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.geekbrains.androidstart.notes.R
import com.geekbrains.androidstart.notes.databinding.FragmentAddNoteBinding
import com.geekbrains.androidstart.notes.pojo.Note
import com.geekbrains.androidstart.notes.utils.MySharedPreferences
import com.geekbrains.androidstart.notes.utils.Utils
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class AddNoteFragment : Fragment() {

    private val KEY_NOTES = "NOTES"
    private lateinit var binding: FragmentAddNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_note, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_note -> {
                saveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {
        with(binding) {
            val name = etAddNoteName.text.toString()
            val description = etAddNoteDescription.text.toString()
            val date = Utils().dateToString(Utils().getCurrentDateTime(), "dd.MM.yyyy")
            val newNote = Note(name, description, date)

            val type: Type = object : TypeToken<ArrayList<Note>?>() {}.type
            val notes = MySharedPreferences(requireContext(), KEY_NOTES).getArrayList<Note>(type)

            if (notes != null) {
                notes.add(newNote)
                MySharedPreferences(requireContext(), KEY_NOTES).saveArrayList(notes)
            } else {
                val newNotes = arrayListOf<Note>()
                newNotes.add(newNote)
                MySharedPreferences(requireContext(), KEY_NOTES).saveArrayList(newNotes)
            }
        }
    }
}