package com.internshala.notesinternshala.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.internshala.notesinternshala.models.Note;

import java.util.ArrayList;
import java.util.Date;

import static com.internshala.notesinternshala.db.DbStrings.CMD_CREATE_TABLE_INE;
import static com.internshala.notesinternshala.db.DbStrings.COMMA;
import static com.internshala.notesinternshala.db.DbStrings.LBR;
import static com.internshala.notesinternshala.db.DbStrings.ORDER_DESC;
import static com.internshala.notesinternshala.db.DbStrings.RBR;
import static com.internshala.notesinternshala.db.DbStrings.TERM;
import static com.internshala.notesinternshala.db.DbStrings.TYPE_INT;
import static com.internshala.notesinternshala.db.DbStrings.TYPE_INT_PK_AI;
import static com.internshala.notesinternshala.db.DbStrings.TYPE_TEXT;

/**
 * Created by piyush0 on 27/04/17.
 */

public class NotesTable {

    private static final String TABLE_NAME = "notes";

    private interface Columns {
        String ID = "id";
        String TEXT = "text";
        String UPDATED_AT = "updatedAt";
        String USER_ID = "userId";
    }

    public static String CMD_CREATE_TABLE =
            CMD_CREATE_TABLE_INE + TABLE_NAME + LBR
                    + Columns.ID + TYPE_INT_PK_AI + COMMA
                    + Columns.TEXT + TYPE_TEXT + COMMA
                    + Columns.UPDATED_AT + TYPE_TEXT + COMMA
                    + Columns.USER_ID + TYPE_INT + COMMA
                    + " FOREIGN KEY (" + Columns.USER_ID + ") REFERENCES " + UserTable.TABLE_NAME + "(" + UserTable.Columns.ID + "));";


    public static long addNewNote(SQLiteDatabase db, Note note, Long userId) {

        ContentValues cv = new ContentValues();
        cv.put(Columns.TEXT, note.getText());
        cv.put(Columns.UPDATED_AT, note.getUpdatedAt().toString());
        cv.put(Columns.USER_ID, userId);

        return db.insert(TABLE_NAME, null, cv);
    }

    private static String[] FULL_PROJECTION = {
            Columns.ID,
            Columns.TEXT,
            Columns.UPDATED_AT
    };

    public static ArrayList<Note> getAllNotes(SQLiteDatabase db, Long userId) {

        ArrayList<Note> notes = new ArrayList<>();

        String whereClause = Columns.USER_ID + "=?";
        String[] whereArgs = {userId.toString()};

        Cursor c = db.query(TABLE_NAME, FULL_PROJECTION, whereClause, whereArgs, null, null, Columns.UPDATED_AT + ORDER_DESC);

        int colID = c.getColumnIndex(Columns.ID);
        int colText = c.getColumnIndex(Columns.TEXT);
        int colDate = c.getColumnIndex(Columns.UPDATED_AT);

        while (c.moveToNext()) {
            notes.add(new Note(c.getString(colText), new Date(c.getString(colDate)), c.getInt(colID)));
        }
        c.close();
        return notes;
    }

    public static void updateNote(SQLiteDatabase db, Integer id, String newText) {
        ContentValues cv = new ContentValues();
        cv.put(Columns.ID, id);
        cv.put(Columns.TEXT, newText);
        cv.put(Columns.UPDATED_AT, (new Date()).toString());
        db.update(TABLE_NAME, cv, "id=" + id, null);
    }

    public static void deleteNote(SQLiteDatabase db, Note note) {
        String Selection = Columns.ID + " LIKE ?";
        String[] SelectionArgs = {String.valueOf(note.getId())};
        db.delete(TABLE_NAME, Selection, SelectionArgs);
    }
}
