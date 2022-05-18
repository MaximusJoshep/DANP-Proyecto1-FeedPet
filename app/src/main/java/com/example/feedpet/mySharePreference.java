package com.example.feedpet;

import android.content.SharedPreferences;

public class mySharePreference {

    public SharedPreferences preferences;;
    public SharedPreferences.Editor editor;

    public mySharePreference(SharedPreferences tmp) {
        preferences=tmp;
        editor=preferences.edit();
    }

    public boolean revisarSesion()
    {
        return this.preferences.getBoolean("sesion", false);
    }

    public void guardarSesion(boolean check, String user, String dni)
    {
        editor.putBoolean("sesion", check);
        editor.putString("user",user);
        editor.putString("dni",dni);
        editor.apply();


    }
}
