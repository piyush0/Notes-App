package com.internshala.notesinternshala.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.internshala.notesinternshala.db.tables.UserTable;
import com.internshala.notesinternshala.models.User;

/**
 * Created by piyush0 on 29/04/17.
 */

public class SignUpFragment extends Fragment {
    EditText et_username, et_password, et_password_re;
    Button btn_sign_up;
    SharedPreferences sharedPreferences;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        findViews(view);
        final SQLiteDatabase notesDb = (new NotesDbHelper(getContext())).getWritableDatabase();
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFields()) {
                    if (UserTable.userExists(notesDb, et_username.getText().toString())) {
                        Toast.makeText(getContext(), "Username already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        long id = UserTable.addNewUser(notesDb, new User(et_username.getText().toString(), et_password.getText().toString()));
                        sharedPreferences = getContext().getSharedPreferences(getString(R.string.shared_prefs_login), Context.MODE_PRIVATE);
                        sharedPreferences.edit().putLong(MainActivity.LOGGED_IN, id).apply();
                        getFragmentManager().beginTransaction().replace(R.id.container, NotesFragment.newInstance()).commit();
                    }
                } else {
                    Toast.makeText(getContext(), "Incorrect Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void findViews(View view) {
        et_username = (EditText) view.findViewById(R.id.et_sign_up_username);
        et_password = (EditText) view.findViewById(R.id.et_sign_up_password);
        et_password_re = (EditText) view.findViewById(R.id.et_sign_up_pass_re);
        btn_sign_up = (Button) view.findViewById(R.id.btn_sign_up);
    }

    private boolean checkFields() {
        if (!et_password.getText().toString().equals(et_password_re.getText().toString())) {
            return false;
        }
        return et_username.getText().toString().trim().length() > 0
                && et_password.getText().toString().trim().length() > 0;
    }

}
