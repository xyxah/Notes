package com.geekbrains.androidstart.notes.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekbrains.androidstart.notes.R
import com.geekbrains.androidstart.notes.databinding.FragmentNotesBinding
import com.geekbrains.androidstart.notes.pojo.Note
import com.geekbrains.androidstart.notes.utils.NotesRecyclerViewAdapter
import com.geekbrains.androidstart.notes.utils.SaveNote


class NotesFragment : Fragment() {

    private val KEY_NOTES = "NOTES"
    private val CURRENT_NOTE = "Current_note"
    private var currentNote: Note? = null

    private lateinit var sn: SaveNote
    private lateinit var rvNotesAdapter: NotesRecyclerViewAdapter
    private lateinit var binding: FragmentNotesBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentNotesBinding.inflate(layoutInflater)
        registerForContextMenu(binding.rvNotes)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(binding.root)
        sn = SaveNote(requireContext())

        if (savedInstanceState != null)
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE)

        initView()

        if (isLandscape())
            currentNote?.let { navigateToNoteLandscape(it) }
    }

    private fun initView() {
        setupRecyclerViewNotes()

        binding.fabAddNote.setOnClickListener {
            navigateToAddNote()
        }
    }

    private fun setupRecyclerViewNotes() {
        if (sn.notes != null) {
            rvNotesAdapter = NotesRecyclerViewAdapter(sn.notes!!)
            rvNotesAdapter.onClick = onClickRecyclerItems()

            binding.rvNotes.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = rvNotesAdapter
            }
        } else Toast.makeText(
            context,
            getString(R.string.toast_message_notes_list_clear),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun onClickRecyclerItems(): NotesRecyclerViewAdapter.OnClickListener {
        return object : NotesRecyclerViewAdapter.OnClickListener {
            override fun onNoteClick(note: Note) {
                currentNote = note
                navigateToNote(note)
            }

            override fun onNoteLongClick(view: View, note: Note) {
                currentNote = note
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

    // Урок 11
    // 3. Создайте контекстное меню для изменения и удаления заметок.

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = requireActivity().menuInflater
        inflater.inflate(R.menu.note_menu, menu)
    }

    // Урок 12
    // Пример работы со списком заметок

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_update -> {
                currentNote?.let { navigateToNote(it) }
                return true
            }
            R.id.action_delete -> {
                Toast.makeText(
                    context,
                    getString(R.string.toast_message_delete_note),
                    Toast.LENGTH_SHORT
                ).show()
                sn.deleteNote(currentNote?.name)
                setupRecyclerViewNotes()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(CURRENT_NOTE, currentNote)
        super.onSaveInstanceState(outState)
    }

    private fun isLandscape(): Boolean {
        return (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
    }
}