class NotesRepository(private val fileNotebook: FileNotebook) {
    fun getNotes(): List<Note> = fileNotebook.getNotes()
    
    fun addNote(note: Note) = fileNotebook.addNote(note)
    
    fun loadNotes() = fileNotebook.loadFromFile()
}
