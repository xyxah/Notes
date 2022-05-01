package com.geekbrains.androidstart.notes

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class NotesFragment : Fragment() {

    private val CURRENT_NOTE = "Current_note"
    private var currentNote: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null)
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE)

        initList()

        if (isLandscape())
            currentNote?.let { navigateToNoteLandscape(it) }
    }

    private fun initList() {
        if (arguments != null) {
            val notes: Array<Note> = requireArguments().getParcelableArray("notes") as Array<Note>
            val layoutView = view as LinearLayout

            for (note in notes) {
                val headerView = View.inflate(context, R.layout.note_item, null)

                val name: TextView = headerView.findViewById(R.id.tv_note_name)
                name.text = note.name

                val date: TextView = headerView.findViewById(R.id.tv_note_date)
                date.text = note.date

                layoutView.addView(headerView)

                headerView.setOnClickListener {
                    currentNote = note
                    navigateToNote(note)
                }
            }
        }
    }

    private fun navigateToNote(note: Note) {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            navigateToNoteLandscape(note)
        else
            navigateToNotePortrait(note)
    }


    private fun navigateToNotePortrait(note: Note) {
        val noteFragment = NoteFragment()
        val bundle = Bundle()
        bundle.putParcelable("note", note)
        noteFragment.arguments = bundle

        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container, noteFragment)
        fragmentTransaction.addToBackStack("")
        fragmentTransaction.commit()
    }

    private fun navigateToNoteLandscape(note: Note) {
        val noteFragment = NoteFragment()
        val bundle = Bundle()
        bundle.putParcelable("note", note)
        noteFragment.arguments = bundle

        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.second_fragment_container, noteFragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(CURRENT_NOTE, currentNote)
        super.onSaveInstanceState(outState)
    }

    private fun isLandscape(): Boolean {
        return (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
    }
}