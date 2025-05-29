import androidx.lifecycle.ViewModel

class NoteViewModel(private val notebook: FileNotebook) : ViewModel() {
    fun getNotes() = notebook.getNotes()
    
    fun addNote(note: Note) = notebook.addNote(note)
    
    fun deleteNote(noteId: String) = notebook.deleteNote(noteId)
    
    fun loadNotes() = notebook.loadFromFile()
}
