package com.geekbrains.androidstart.notes.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekbrains.androidstart.notes.R
import com.geekbrains.androidstart.notes.databinding.FragmentNotesBinding
import com.geekbrains.androidstart.notes.pojo.Note
import com.geekbrains.androidstart.notes.utils.MySharedPreferences
import com.geekbrains.androidstart.notes.utils.NotesRecyclerViewAdapter
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class NotesFragment : Fragment() {

    private val KEY_NOTES = "NOTES"
    private val CURRENT_NOTE = "Current_note"
    private var currentNote: Note? = null

    private lateinit var binding: FragmentNotesBinding
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentNotesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(binding.root)

        if (savedInstanceState != null)
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE)

        initView()

        if (isLandscape())
            currentNote?.let { navigateToNoteLandscape(it) }
    }

    private fun initView() {
        val type: Type = object : TypeToken<ArrayList<Note>?>() {}.type
        val notes: ArrayList<Note>? =
            MySharedPreferences(requireContext(), KEY_NOTES).getArrayList(type)
        if (notes != null) {
            val rvNotesAdapter = NotesRecyclerViewAdapter(notes)
            rvNotesAdapter.onClick = onClickRecyclerItems()

            binding.rvNotes.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = rvNotesAdapter
            }
        }

        binding.fabAddNote.setOnClickListener {
            navigateToAddNote()
        }
    }

    private fun onClickRecyclerItems(): NotesRecyclerViewAdapter.OnClickListener {
        return object : NotesRecyclerViewAdapter.OnClickListener {
            override fun onNoteClick(note: Note) {
                currentNote = note
                navigateToNote(note)
            }
        }
    }

    private fun navigateToNote(note: Note) {
        if (isLandscape()) navigateToNoteLandscape(note)
        else navigateToNotePortrait(note)
    }

    private fun navigateToNotePortrait(note: Note) {
        val bundle = Bundle()
        bundle.putParcelable("note", note)
        navController.navigate(R.id.action_notesFragment_to_noteFragment, bundle)
    }

    private fun navigateToNoteLandscape(note: Note) {
        val noteFragment = NoteFragment()
        val bundle = Bundle()
        bundle.putParcelable("note", note)
        noteFragment.arguments = bundle

        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.apply {
            replace(R.id.second_fragment_container, noteFragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            commit()
        }
    }

    private fun navigateToAddNote() {
        if (isLandscape()) {
            val addNoteFragment = AddNoteFragment()
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.apply {
                replace(R.id.second_fragment_container, addNoteFragment)
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                commit()
            }
        } else navController.navigate(R.id.action_notesFragment_to_addNoteFragment)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(CURRENT_NOTE, currentNote)
        super.onSaveInstanceState(outState)
    }

    private fun isLandscape(): Boolean {
        return (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
    }
}