package com.internshala.notesinternshala.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.internshala.notesinternshala.R;
import com.internshala.notesinternshala.db.NotesDbHelper;
import com.internshala.notesinternshala.db.tables.NotesTable;
import com.internshala.notesinternshala.fragments.EditFragment;
import com.internshala.notesinternshala.models.Note;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by piyush0 on 27/04/17.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private ArrayList<Note> notes;
    private Context context;
    private FragmentManager fragmentManager;

    public NotesAdapter(ArrayList<Note> notes, Context context, FragmentManager fragmentManager) {
        this.notes = notes;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.list_item_note, parent, false);

        NotesViewHolder notesViewHolder = new NotesViewHolder(view);
        notesViewHolder.tv_note_text = (TextView) view.findViewById(R.id.tv_note_text);
        notesViewHolder.tv_note_date = (TextView) view.findViewById(R.id.tv_note_date);
        notesViewHolder.btn_note_edit = (Button) view.findViewById(R.id.btn_note_edit);
        notesViewHolder.btn_note_delete = (Button) view.findViewById(R.id.btn_note_delete);
        return notesViewHolder;
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {
        final Note note = notes.get(position);
        holder.tv_note_text.setText(note.getText());
        holder.tv_note_date.setText(prettyDate(note.getUpdatedAt()));
        holder.btn_note_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.container, EditFragment.newInstance(false, note.getText(), note.getId()))
                        .commit();
            }
        });
        holder.btn_note_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase notesDb = (new NotesDbHelper(context)).getWritableDatabase();
                NotesTable.deleteNote(notesDb, note);
                notes = NotesTable.getAllNotes(notesDb);
                notifyDataSetChanged();
            }
        });
    }

    private String prettyDate(Date date){
        return date.getDate() + "/" + date.getMonth() + "/" + (date.getYear()+1900);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        TextView tv_note_text;
        TextView tv_note_date;
        Button btn_note_delete;
        Button btn_note_edit;

        NotesViewHolder(View itemView) {
            super(itemView);
        }
    }
}
