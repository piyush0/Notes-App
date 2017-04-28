package com.internshala.notesinternshala.fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.internshala.notesinternshala.R;
import com.internshala.notesinternshala.activities.MainActivity;
import com.internshala.notesinternshala.db.NotesDbHelper;
import com.internshala.notesinternshala.db.tables.NotesTable;
import com.internshala.notesinternshala.models.Note;

import java.util.Date;

/**
 * Created by piyush0 on 27/04/17.
 */

public class EditFragment extends Fragment {
    String note_text;
    EditText et_note_text;
    Button btn_save;
    Boolean newNote;
    Integer id;

    public static final String NEW_NOTE = "newNote";
    public static final String NOTE_TEXT = "NoteText";
    public static final String NOTE_ID = "noteId";

    public static EditFragment newInstance(Boolean newNote, String note, Integer id) {
        Bundle args = new Bundle();
        EditFragment editFragment = new EditFragment();
        args.putBoolean(NEW_NOTE, newNote);
        args.putString(NOTE_TEXT, note);
        args.putInt(NOTE_ID, id);
        editFragment.setArguments(args);
        return editFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        this.newNote = getArguments().getBoolean(NEW_NOTE);
        this.note_text = getArguments().getString(NOTE_TEXT);
        this.id = getArguments().getInt(NOTE_ID);
        findViews(view);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFields()) {
                    if (newNote) {
                        //Add new note
                        SQLiteDatabase notesDb = (new NotesDbHelper(getContext())).getWritableDatabase();
                        NotesTable.addNewNote(notesDb,
                                new Note(et_note_text.getText().toString(), new Date()),
                                getContext().getSharedPreferences(getString(R.string.shared_prefs_login), Context.MODE_PRIVATE)
                                        .getLong(MainActivity.LOGGED_IN, -1));
                    } else {
                        //Update note
                        SQLiteDatabase notesDb = (new NotesDbHelper(getContext())).getWritableDatabase();
                        NotesTable.updateNote(notesDb, id, et_note_text.getText().toString());
                    }

                    getFragmentManager().beginTransaction().replace(R.id.container, NotesFragment.newInstance()).commit();
                } else {
                    Toast.makeText(getContext(), "Empty Note!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private boolean checkFields() {
        return et_note_text.getText().toString().trim().length() > 0;
    }


    private void findViews(View view) {
        et_note_text = (EditText) view.findViewById(R.id.et_note_edit);
        btn_save = (Button) view.findViewById(R.id.btn_save);
        et_note_text.setText(note_text);

        if (et_note_text.getText().toString().equals("")) {
            et_note_text.setHint("Add a new note... ");
        }

    }
}
