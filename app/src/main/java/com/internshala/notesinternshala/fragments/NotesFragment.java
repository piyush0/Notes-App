package com.internshala.notesinternshala.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.internshala.notesinternshala.adapters.NotesAdapter;
import com.internshala.notesinternshala.R;
import com.internshala.notesinternshala.db.NotesDbHelper;
import com.internshala.notesinternshala.db.tables.NotesTable;
import com.internshala.notesinternshala.models.Note;

import java.util.ArrayList;

/**
 * Created by piyush0 on 26/04/17.
 */

public class NotesFragment extends Fragment {
    ArrayList<Note> notes;
    RecyclerView rvNotes;
    SQLiteDatabase notesDb;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_add) {
            getFragmentManager().beginTransaction().replace(R.id.container, EditFragment.newInstance(true, "", 0)).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        setHasOptionsMenu(true);
        notesDb = (new NotesDbHelper(getContext())).getWritableDatabase();
        notes = NotesTable.getAllNotes(notesDb);
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        rvNotes = (RecyclerView) view.findViewById(R.id.rv_notes);
        rvNotes.setLayoutManager(new LinearLayoutManager(getContext()));
        rvNotes.setAdapter(new NotesAdapter(notes, getContext(), getFragmentManager()));
    }
}
