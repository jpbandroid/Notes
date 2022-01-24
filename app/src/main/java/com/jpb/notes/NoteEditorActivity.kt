package com.jpb.notes

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.HashSet


class NoteEditorActivity : AppCompatActivity() {
    var noteID = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_editor)
        val editText = findViewById<View>(R.id.editText) as EditText
        val intent = intent
        noteID = intent.getIntExtra("noteID", -1)
        if (noteID != -1) {
            editText.setText(MainActivity.notes[noteID])
        } else {
            MainActivity.notes.add("") // as initially, the note is empty
            noteID = MainActivity.notes.size - 1
            MainActivity.arrayAdapter!!.notifyDataSetChanged()
        }
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                MainActivity.notes[noteID] = s.toString()
                MainActivity.arrayAdapter!!.notifyDataSetChanged()
                val sharedPreferences = applicationContext.getSharedPreferences(
                    "com.jpb.notes",
                    MODE_PRIVATE
                )
                val set = HashSet(MainActivity.notes)
                sharedPreferences.edit().putStringSet("notes", set).apply()
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }
}