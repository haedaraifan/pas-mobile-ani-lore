package com.example.ani_lore;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Preferences {
    private SharedPreferences pref;
    private Editor editor;
    private final String sessionLogin ="session_login";

    public Preferences(Context context) {
        pref = context.getSharedPreferences("MyPref", context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setSessionLogin(boolean value) {
        editor.putBoolean(sessionLogin, value);
        editor.commit();
    }

    public boolean getSessionLogin() {
        return pref.getBoolean(sessionLogin, false);
    }
}
