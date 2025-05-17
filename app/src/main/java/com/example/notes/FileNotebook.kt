import android.content.Context
import android.util.Log
import org.json.JSONArray
import java.io.File
import java.util.Timer
import java.util.TimerTask

class FileNotebook(private val context: Context) {

    private val notes = mutableListOf<Note>()
    private val TAG = "FileNotebook"

    fun getNotes(): List<Note> = notes.toList()

    fun addNote(note: Note) {
        Log.d(TAG, "Adding note: ${note.uid}")
        notes.add(note)
    }

    fun removeNoteByUid(uid: String) {
        Log.d(TAG, "Removing note with UID: $uid")
        notes.removeAll { it.uid == uid }
    }

    fun saveToFile(filename: String = "notebook.json") {
        val file = File(context.filesDir, filename)
        val jsonArray = JSONArray()
        notes.forEach { jsonArray.put(it.toJson()) }
        file.writeText(jsonArray.toString())
        Log.d(TAG, "Saved ${notes.size} notes to file")
    }

    fun loadFromFile(filename: String = "notebook.json") {
        val file = File(context.filesDir, filename)
        if (!file.exists()) {
            Log.d(TAG, "File not found: $filename")
            return
        }
        val content = file.readText()
        val jsonArray = JSONArray(content)
        notes.clear()
        for (i in 0 until jsonArray.length()) {
            Note.parse(jsonArray.getJSONObject(i))?.let {
                notes.add(it)
                Log.d(TAG, "Loaded note: ${it.uid}")
            }
        }
    }

    fun scheduleNoteDeletion(uid: String, delayMillis: Long = 60000) {
        Log.d(TAG, "Scheduled deletion for UID: $uid after $delayMillis ms")
        Timer().schedule(object : TimerTask() {
            override fun run() {
                removeNoteByUid(uid)
                Log.d(TAG, "Auto-deleted note with UID: $uid")
            }
        }, delayMillis)
    }
}