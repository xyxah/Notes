package com.geekbrains.androidstart.notes

import android.app.AlertDialog
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.geekbrains.androidstart.notes.pojo.Note
import com.geekbrains.androidstart.notes.utils.MySharedPreferences
import com.geekbrains.androidstart.notes.utils.Utils
import com.google.android.material.navigation.NavigationView
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class MainActivity : AppCompatActivity() {

    private val KEY_NOTES = "NOTES"
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        //setupActionBarWithNavController(navController)
        initToolbarAndDrawer()
    }

    private fun initToolbarAndDrawer() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        initDrawer(toolbar)
    }

    private fun initDrawer(toolbar: Toolbar) {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_drawer_notes -> {
                    navController.navigate(R.id.notesFragment)
                    drawer.closeDrawer(GravityCompat.START)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_drawer_add_note -> {
                    navController.navigate(R.id.addNoteFragment)
                    drawer.closeDrawer(GravityCompat.START)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_drawer_about -> {
                    navController.navigate(R.id.aboutFragment)
                    drawer.closeDrawer(GravityCompat.START)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_drawer_exit -> {
                    closeAppDialog()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }

    private fun closeAppDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.close_app_dialog_title))
        builder.setMessage(getString(R.string.close_app_dialog_message))

        builder.setPositiveButton(getString(R.string.yes)) { dialog, which ->
            finish()
        }
        builder.setNegativeButton(getString(R.string.no)) { dialog, which ->
            Toast.makeText(
                this,
                getString(R.string.close_app_dialog_negative_btn_message),
                Toast.LENGTH_SHORT
            ).show()
        }

        builder.show()
    }

    fun saveNote(oldNoteName: String?, etNoteName: EditText, etNoteDescription: EditText) {
        val name = etNoteName.text.toString()
        val description = etNoteDescription.text.toString()

        val utils = Utils()
        val date = utils.dateToString(utils.getCurrentDateTime(), "dd.MM.yyyy")
        val newNote = Note(name, description, date)

        val type: Type = object : TypeToken<ArrayList<Note>?>() {}.type
        val notes = MySharedPreferences(this, KEY_NOTES).getArrayList<Note>(type)

        if (notes != null) {
            //Поиск и удаление старой заметки
            if (oldNoteName != null) {
                val findedNote = notes.find {
                    it.name == oldNoteName
                }
                notes.remove(findedNote)
            }

            //Добавление новой заметки
            notes.add(newNote)
            MySharedPreferences(this, KEY_NOTES).saveArrayList(notes)
        } else {
            // Создание нового списка
            val newNotes = arrayListOf<Note>()
            newNotes.add(newNote)
            MySharedPreferences(this, KEY_NOTES).saveArrayList(newNotes)
        }

        Toast.makeText(this, getString(R.string.toast_message_add_note), Toast.LENGTH_SHORT).show()
        navController.popBackStack()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navController.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}