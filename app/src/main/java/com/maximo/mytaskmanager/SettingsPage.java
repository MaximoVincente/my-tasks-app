package com.maximo.mytaskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsPage extends AppCompatActivity {
    SharedPreferences preferences;
    public static final String USERNAME_TAG = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        saveUserSettingsValuesToSharedPrefs();
    }

    public void saveUserSettingsValuesToSharedPrefs(){
        // Setup the editor -> sharedPrefs is read only by default
        SharedPreferences.Editor preferenceEditor = preferences.edit();
        Button saveButton = SettingsPage.this.findViewById(R.id.ActivitySettingsPageButtonSave);
        saveButton.setOnClickListener(v -> {
            String usernameText = ((EditText) findViewById(R.id.ActivitySettingsPageEditTextUsername)).getText().toString();
            preferenceEditor.putString(USERNAME_TAG, usernameText);
            preferenceEditor.apply();

            Toast.makeText(this, "Settings Saved!", Toast.LENGTH_SHORT).show();
        });
    }

}