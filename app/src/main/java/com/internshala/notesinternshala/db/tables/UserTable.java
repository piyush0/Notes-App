package com.internshala.notesinternshala.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.internshala.notesinternshala.models.User;

import static com.internshala.notesinternshala.db.DbStrings.CMD_CREATE_TABLE_INE;
import static com.internshala.notesinternshala.db.DbStrings.COMMA;
import static com.internshala.notesinternshala.db.DbStrings.LBR;
import static com.internshala.notesinternshala.db.DbStrings.RBR;
import static com.internshala.notesinternshala.db.DbStrings.TERM;
import static com.internshala.notesinternshala.db.DbStrings.TYPE_INT_PK_AI;
import static com.internshala.notesinternshala.db.DbStrings.TYPE_TEXT;

/**
 * Created by piyush0 on 29/04/17.
 */

public class UserTable {

    public static final String TABLE_NAME = "users";

    interface Columns {
        String ID = "id";
        String USERNAME = "username";
        String PASSWORD = "password";
    }

    public static String CMD_CREATE_TABLE =
            CMD_CREATE_TABLE_INE + TABLE_NAME + LBR
                    + UserTable.Columns.ID + TYPE_INT_PK_AI + COMMA
                    + Columns.USERNAME + TYPE_TEXT + COMMA
                    + Columns.PASSWORD + TYPE_TEXT
                    + RBR + TERM;


    public static Long addNewUser(SQLiteDatabase db, User user) {

        ContentValues cv = new ContentValues();
        cv.put(Columns.USERNAME, user.getUsername());
        cv.put(Columns.PASSWORD, user.getPassword());

        return db.insert(TABLE_NAME, null, cv);
    }

    public static boolean userExists(SQLiteDatabase db, String username) {
        Cursor c = db.query(TABLE_NAME, FULL_PROJECTION, Columns.USERNAME + "=" + "\"" + username + "\"", null, null, null, null, null);
        return c.getCount() > 0;
    }

    public static Long checkUser(SQLiteDatabase db, String username, String password) {
        String whereClause = Columns.USERNAME + "=? AND " + Columns.PASSWORD + "=?";
        String[] whereArgs = {username, password};

        Cursor c = db.query(TABLE_NAME, FULL_PROJECTION, whereClause, whereArgs, null, null, null);
        int colID = c.getColumnIndex(Columns.ID);
        long id = -1;

        while (c.moveToNext()) {
            id = c.getInt(colID);
        }
        c.close();
        return id;
    }

    private static String[] FULL_PROJECTION = {
            Columns.ID,
            Columns.USERNAME,
            Columns.PASSWORD
    };
}
