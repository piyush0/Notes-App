package com.internshala.notesinternshala.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.internshala.notesinternshala.db.tables.NotesTable;
import com.internshala.notesinternshala.db.tables.UserTable;

/**
 * Created by piyush0 on 27/04/17.
 */

public class NotesDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "notes.db";
    private static final int DB_VER = 1;

    public NotesDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserTable.CMD_CREATE_TABLE);
        db.execSQL(NotesTable.CMD_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
