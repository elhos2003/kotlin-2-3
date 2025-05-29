import android.content.Context
import org.json.JSONArray
import org.json.JSONException
import java.io.File

class FileNotebook(private val context: Context) {
    private val notes = mutableListOf<Note>()
    private val notesFile = File(context.filesDir, "notes.json")

    fun addNote(note: Note) {
        notes.add(note)
        saveToFile()
    }

    fun deleteNote(noteId: String): Boolean {
        val isRemoved = notes.removeIf { it.uid == noteId }
        if (isRemoved) saveToFile()
        return isRemoved
    }

    fun getNotes(): List<Note> = notes.toList()

    fun loadFromFile() {
        if (!notesFile.exists()) return
        
        try {
            notes.clear()
            JSONArray(notesFile.readText()).let { jsonArray ->
                for (i in 0 until jsonArray.length()) {
                    Note.parse(jsonArray.getJSONObject(i))?.let { notes.add(it) }
                }
            }
        } catch (e: JSONException) {
        }
    }

    private fun saveToFile() {
        try {
            notesFile.writeText(
                JSONArray().apply { 
                    notes.forEach { put(it.json) } 
                }.toString()
            )
        } catch (e: Exception) {
        }
    }
}
