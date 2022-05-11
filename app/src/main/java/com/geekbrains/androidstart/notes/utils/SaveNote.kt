package com.geekbrains.androidstart.notes.utils

import android.content.Context
import com.geekbrains.androidstart.notes.pojo.Note
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SaveNote(context: Context) {

    private val KEY_NOTES = "NOTES"
    private val type: Type = object : TypeToken<ArrayList<Note>?>() {}.type
    val notes = MySharedPreferences(context, KEY_NOTES).getArrayList<Note>(type)

    fun saveNote(context: Context,oldNoteName: String?, name: String, description: String){
        val utils = Utils()
        val date = utils.dateToString(utils.getCurrentDateTime(), "dd.MM.yyyy")
        val newNote = Note(name, description, date)

        if (notes != null) {
            deleteNote(oldNoteName)
            addNote(context,newNote)
        } else {
            newListNotes(context, newNote)
        }
    }

    private fun deleteNote(oldName: String?){
        val foundNote = notes?.find {
            it.name == oldName
        }
        notes?.remove(foundNote)
    }

    private fun addNote(context: Context, note: Note){
        notes?.add(note)
        MySharedPreferences(context, KEY_NOTES).saveArrayList(notes)
    }

    private fun newListNotes(context: Context, note: Note){
        val newNotes = arrayListOf<Note>()
        newNotes.add(note)
        MySharedPreferences(context, KEY_NOTES).saveArrayList(newNotes)
    }
}