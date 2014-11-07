package com.gannon.settingsdemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

/**
 * Abstract Activity Base to inherit common members
 */
public abstract class Base extends Activity
{
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        prefs  = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        super.onCreate(savedInstanceState);
    }

    /**
     * Application-wide Shared Preferences Viewer
     * @return SharedPreferences app prefs
     */
    protected SharedPreferences getPrefs()
    {
        return prefs;
    }

    /**
     * Application-wide Shared Preferences Editor
     * @return SharedPreferences app prefs editor
     */
    protected SharedPreferences.Editor getEditor()
    {
        return editor;
    }
}
