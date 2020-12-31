package ng.com.plustech.architetureexample;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Note note) {
        new NoteCRUDAsyncTask(noteDao, "insert").execute(note);
    }

    public void update(Note note) {
        new NoteCRUDAsyncTask(noteDao, "update").execute(note);
    }

    public void delete(Note note) {
        new NoteCRUDAsyncTask(noteDao, "delete").execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    private static class NoteCRUDAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;
        private String crud;

        private NoteCRUDAsyncTask(NoteDao noteDao, String crud) {
            this.noteDao = noteDao;
            this.crud = crud;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            if(crud.equals("insert")) {
                noteDao.insert(notes[0]);
            } else if(crud.equals("update")) {
                noteDao.update(notes[0]);
            } else if(crud.equals("delete")) {
                noteDao.delete(notes[0]);
            }
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;
        private String crud;

        private DeleteAllNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
            this.crud = crud;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
