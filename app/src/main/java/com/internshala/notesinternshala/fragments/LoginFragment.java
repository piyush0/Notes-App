package com.internshala.notesinternshala.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.internshala.notesinternshala.activities.MainActivity;
import com.internshala.notesinternshala.R;
import com.internshala.notesinternshala.db.NotesDbHelper;
import com.internshala.notesinternshala.db.tables.UserTable;
import com.internshala.notesinternshala.models.User;

/**
 * Created by piyush0 on 26/04/17.
 */

public class LoginFragment extends Fragment {

    EditText et_username, et_password;
    Button btn_sign_in;
    TextView tv_sign_up;
    SharedPreferences sharedPreferences;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        findViews(view);
        sharedPreferences = getContext().getSharedPreferences(getString(R.string.shared_prefs_login), Context.MODE_PRIVATE);
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctFields() != -1) {
                    sharedPreferences
                            .edit()
                            .putLong(MainActivity.LOGGED_IN, correctFields())
                            .apply();

                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, NotesFragment.newInstance())
                            .commit();
                } else {
                    Toast.makeText(getContext(), "Incorrect Fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, SignUpFragment.newInstance()).commit();
            }
        });
        return view;
    }

    private Long correctFields() {
        SQLiteDatabase notesDb = (new NotesDbHelper(getContext())).getWritableDatabase();
        return UserTable.checkUser(notesDb, et_username.getText().toString(), et_password.getText().toString());
    }

    private void findViews(View view) {
        tv_sign_up = (TextView) view.findViewById(R.id.tv_sign_up);
        et_username = (EditText) view.findViewById(R.id.et_username);
        et_password = (EditText) view.findViewById(R.id.et_password);
        btn_sign_in = (Button) view.findViewById(R.id.btn_sign_in);


        SpannableString content = new SpannableString("Sign Up");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tv_sign_up.setText(content);
    }
}
