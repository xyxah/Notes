package com.geekbrains.androidstart.notes.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.geekbrains.androidstart.notes.R
import com.geekbrains.androidstart.notes.databinding.FragmentAddNoteBinding
import com.geekbrains.androidstart.notes.utils.SaveNote

class AddNoteFragment : Fragment() {

    private lateinit var binding: FragmentAddNoteBinding
    private lateinit var navController: NavController
    private lateinit var sn: SaveNote

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar_menu, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sn = SaveNote(requireContext())
        navController = Navigation.findNavController(view)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_note -> {
                val sn = SaveNote(requireContext())
                sn.saveNote(
                    requireContext(), null,
                    binding.etAddNoteName.text.toString(),
                    binding.etAddNoteDescription.text.toString()
                )
                navController.popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}