package com.example.notes.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.notes.data.FileNotebook
import com.example.notes.ui.theme.NotesTheme

class MainActivity : ComponentActivity() {
    private lateinit var notebook: FileNotebook

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        notebook = FileNotebook(this)
        notebook.loadFromFile()

        setContent {
            NotesTheme {
                // UI implementation here
            }
        }
    }

    override fun onDestroy() {
        notebook.saveToFile()
        super.onDestroy()
    }
}
