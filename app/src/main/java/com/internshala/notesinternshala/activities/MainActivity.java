package com.internshala.notesinternshala.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.internshala.notesinternshala.R;
import com.internshala.notesinternshala.fragments.LoginFragment;
import com.internshala.notesinternshala.fragments.NotesFragment;
import com.internshala.notesinternshala.utils.FontsOverride;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    SharedPreferences sharedPreferences;

    public static final String LOGGED_IN = "loggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FontsOverride.applyFontForToolbarTitle(this, FontsOverride.FONT_PROXIMA_NOVA, getWindow());

        sharedPreferences = getSharedPreferences(getString(R.string.shared_prefs_login), Context.MODE_PRIVATE);
        fragmentManager = getSupportFragmentManager();

        if (!sharedPreferences.getBoolean(LOGGED_IN, false)) {
            //User is logged out
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commit();
        } else {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction
                    .replace(R.id.container, NotesFragment.newInstance())
                    .commit();
        }
    }

}
