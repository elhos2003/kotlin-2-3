package com.example.notes.data

import android.content.Context
import com.example.notes.models.Note
import org.json.JSONArray
import org.slf4j.LoggerFactory
import java.io.File

class FileNotebook(private val context: Context) {
    private val logger = LoggerFactory.getLogger(FileNotebook::class.java)
    private val notes = mutableListOf<Note>()
    private val notesFile by lazy { File(context.filesDir, "notes.json") }

    init {
        logger.info("FileNotebook initialized with path: ${context.filesDir}")
    }

    fun addNote(note: Note) {
        notes.add(note)
        logger.debug("Note added: ${note.title}")
    }

    fun removeNote(uid: String): Boolean {
        val result = notes.removeIf { it.uid == uid }
        if (result) {
            logger.debug("Note removed with uid: $uid")
        } else {
            logger.warn("Note with uid $uid not found for removal")
        }
        return result
    }

    fun saveToFile() {
        try {
            val jsonArray = JSONArray().apply {
                notes.forEach { put(it.json) }
            }
            notesFile.writeText(jsonArray.toString())
            logger.info("Notes saved to file. Total notes: ${notes.size}")
        } catch (e: Exception) {
            logger.error("Error saving notes to file", e)
        }
    }

    fun loadFromFile() {
        try {
            if (!notesFile.exists()) {
                logger.info("No notes file found at ${notesFile.path}")
                return
            }

            notes.clear()
            JSONArray(notesFile.readText()).let { jsonArray ->
                for (i in 0 until jsonArray.length()) {
                    Note.parse(jsonArray.getJSONObject(i))?.let { note ->
                        notes.add(note)
                    }
                }
            }
            logger.info("Notes loaded from file. Total notes: ${notes.size}")
        } catch (e: Exception) {
            logger.error("Error loading notes from file", e)
        }
    }
}
