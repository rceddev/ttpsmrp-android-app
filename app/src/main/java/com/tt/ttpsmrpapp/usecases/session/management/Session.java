package com.tt.ttpsmrpapp.usecases.session.management;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    private static final String PREFERENCE_FILE_KEY = "com.tt.ttpsmrapp";
    private static final String KEY_TOKEN = "TOKEN";
    private static final String KEY_LOGGED_IN = "LOGGED_IN";

    public Session(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String token){
        editor.putString(KEY_TOKEN, token);
        editor.putBoolean(KEY_LOGGED_IN, true);
        editor.apply();
    }

    public void endSession(){
        editor.clear();
        editor.commit();
    }

    public String getToken(){
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(KEY_LOGGED_IN, false);
    }
}
