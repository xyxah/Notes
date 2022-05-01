package com.geekbrains.androidstart.notes

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val testNotes = arrayOf(
        Note("Список продуктов", "Молоко, хлеб, лимон", "22.01.2022"),
        Note("Важно!!!", "Не забыть сходить к врачу, запись в 11:30.", "31.02.2022"),
        Note(
            "Расписание",
            "09:00 - Подъём\n09:30 - Завтрак\n11:00 - Начало рабочего дня\n20:00 - Конец рабочего дня\n22:00 - Сон",
            "13.04.2022"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val notesFragment = NotesFragment()
        val bundle = Bundle()
        bundle.putParcelableArray("notes", testNotes)
        notesFragment.arguments = bundle

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, notesFragment).commit()
            supportFragmentManager.beginTransaction()
                .replace(R.id.second_fragment_container, NoteFragment()).commit()
        } else supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, notesFragment).commit()
    }
}