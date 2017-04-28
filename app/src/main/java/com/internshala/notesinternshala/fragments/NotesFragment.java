package com.internshala.notesinternshala.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.internshala.notesinternshala.activities.MainActivity;
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
    SharedPreferences sharedPreferences;

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
        } else if (id == R.id.action_sign_out) {
            sharedPreferences.edit().putLong(MainActivity.LOGGED_IN, -1).apply();
            getFragmentManager().beginTransaction().replace(R.id.container, LoginFragment.newInstance()).commit();
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
        sharedPreferences = getContext().getSharedPreferences(getString(R.string.shared_prefs_login), Context.MODE_PRIVATE);

        notes = NotesTable.getAllNotes(notesDb, sharedPreferences.getLong(MainActivity.LOGGED_IN, -1));
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        rvNotes = (RecyclerView) view.findViewById(R.id.rv_notes);
        rvNotes.setLayoutManager(new LinearLayoutManager(getContext()));
        rvNotes.setAdapter(new NotesAdapter(notes, getContext(), getFragmentManager()));
    }
}
