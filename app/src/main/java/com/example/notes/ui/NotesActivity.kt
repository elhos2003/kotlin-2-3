import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider

class NotesActivity : AppCompatActivity() {
    private val viewModel: NoteViewModel by viewModels {
        ViewModelFactory(FileNotebook(applicationContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        
        viewModel.loadNotes()
    }
}

class ViewModelFactory(private val notebook: FileNotebook) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(notebook) as T
    }
}
