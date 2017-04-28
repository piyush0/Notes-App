package com.internshala.notesinternshala;

import android.app.Application;

import com.internshala.notesinternshala.utils.FontsOverride;

/**
 * Created by piyush0 on 27/04/17.
 */

public class NotesApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/" + FontsOverride.FONT_PROXIMA_NOVA);
    }
}
