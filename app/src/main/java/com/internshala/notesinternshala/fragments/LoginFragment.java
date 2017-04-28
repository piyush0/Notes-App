package com.internshala.notesinternshala.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.internshala.notesinternshala.activities.MainActivity;
import com.internshala.notesinternshala.R;

/**
 * Created by piyush0 on 26/04/17.
 */

public class LoginFragment extends Fragment {

    EditText et_username, et_password;
    Button btn_sign_in;
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
                if (correctFields()) {
                    sharedPreferences
                            .edit()
                            .putBoolean(MainActivity.LOGGED_IN, true)
                            .apply();

                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, NotesFragment.newInstance())
                            .commit();
                } else {
                    Toast.makeText(getContext(), "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private boolean correctFields() {

        return et_username.getText().toString().trim().length() > 0
                && et_password.getText().toString().trim().length() > 0;
    }

    private void findViews(View view) {
        et_username = (EditText) view.findViewById(R.id.et_username);
        et_password = (EditText) view.findViewById(R.id.et_password);
        btn_sign_in = (Button) view.findViewById(R.id.btn_sign_in);
    }
}
