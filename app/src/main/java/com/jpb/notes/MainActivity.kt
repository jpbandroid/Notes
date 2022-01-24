package com.jpb.notes

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemLongClickListener
import androidx.appcompat.app.AppCompatActivity
import java.util.ArrayList
import java.util.HashSet

import com.google.android.material.snackbar.Snackbar

import android.R.layout
import android.widget.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar.make


class MainActivity : AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        if (item.itemId == R.id.add_note) {
            val intent = Intent(applicationContext, NoteEditorActivity::class.java)
            startActivity(intent)
            return true
        }
        if (item.itemId == R.id.info) {
            val intent = Intent(applicationContext, AboutActivity::class.java)
            startActivity(intent)
            return true
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView = findViewById<View>(R.id.listView) as GridView
        val sharedPreferences =
            applicationContext.getSharedPreferences("com.jpb.notes", MODE_PRIVATE)
        val set = sharedPreferences.getStringSet("notes", null) as HashSet<String>?
        if (set == null) {
            notes.add("Welcome to jpb Notes!")
        } else {
            notes =
                ArrayList(set) // to bring all the already stored data in the set to the notes ArrayList
        }
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, notes)
        listView.adapter = arrayAdapter
        listView.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                val intent = Intent(applicationContext, NoteEditorActivity::class.java)
                intent.putExtra("noteID", position)
                startActivity(intent)
            }
        listView.onItemLongClickListener =
            OnItemLongClickListener { parent, view, position, id ->
                MaterialAlertDialogBuilder(this@MainActivity) // we can't use getApplicationContext() here as we want the activity to be the context, not the application
                    .setIcon(R.drawable.ic_round_warning_24)
                    .setTitle(getString(R.string.notedelete))
                    .setMessage(getString(R.string.notedeletemessage))
                    .setPositiveButton(
                        getString(R.string.yes)
                    ) { dialog, which ->
                        // to remove the selected note once "Yes" is pressed
                        notes.removeAt(position)
                        arrayAdapter!!.notifyDataSetChanged()
                        val sharedPreferences = applicationContext.getSharedPreferences(
                            "com.jpb.notes",
                            MODE_PRIVATE
                        )
                        val set = HashSet(notes)
                        sharedPreferences.edit().putStringSet("notes", set).apply()
                        val snackbar = Snackbar
                            .make(
                                view,
                                getString(R.string.snackbarnotedelete),
                                Snackbar.LENGTH_LONG
                            )
                            .setAction(
                                ""
                            )  // If the Undo button
// is pressed, show
// the message using Toast
                            {
                            }

                        snackbar.show()
                    }
                    .setNegativeButton(getString(R.string.notext), null)
                    .show()
                true // this was initially false but we change it to true as if false, the method assumes that we want to do a short click after the long click as well
            }
    }

    companion object {
        var notes = ArrayList<String>()
        var arrayAdapter: ArrayAdapter<String>? = null
    }
}